package com.migros.couriertracking.manager;

import com.migros.couriertracking.dto.ErrorCode;
import com.migros.couriertracking.dto.LocationRequestDTO;
import com.migros.couriertracking.dto.StoreDTO;
import com.migros.couriertracking.entity.CourierMovementHistory;
import com.migros.couriertracking.entity.Store;
import com.migros.couriertracking.mapper.StoreMapper;
import com.migros.couriertracking.repository.dao.CourierDAO;
import com.migros.couriertracking.repository.dao.CourierMovementHistoryDAO;
import com.migros.couriertracking.repository.dao.StoreDAO;
import com.migros.couriertracking.service.CalculatorService;
import com.migros.couriertracking.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

@Component
public class TravelerManager extends Command<LocationRequestDTO> {

    @Autowired
    private CourierDAO courierDAO;

    @Autowired
    private CourierMovementHistoryDAO courierMovementHistoryDAO;

    @Autowired
    private StoreDAO storeDAO;

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private StoreMapper storeMapper;

    private static Logger logger = LoggerFactory.getLogger(TravelerManager.class);

    private StoreDTO nearestStore;


    @Override
    public void execute(LocationRequestDTO locationRequestDTO) {

            validateNearestStore(locationRequestDTO);
            validateLastMovement(locationRequestDTO);

            var courierId = locationRequestDTO.getCourierId();
            var courierMovementHistory = courierMovementHistoryDAO.getCourierMovementHistory(courierId);

            processCourierMovement(locationRequestDTO, courierId, courierMovementHistory);

    }

    private void processCourierMovement(LocationRequestDTO locationRequestDTO, Long courierId, Optional<CourierMovementHistory> courierMovementHistory) {
        if (courierMovementHistory.isEmpty()) {
            createNewCourierMovement(courierId);
        } else {
            updateExistingCourierMovement(locationRequestDTO, courierMovementHistory.get());
        }
    }

    private void validateNearestStore(LocationRequestDTO locationRequestDTO) {
        var nearestStore = findNearestStore(
                locationRequestDTO.getLocation().getLatitude(),
                locationRequestDTO.getLocation().getLongitude()
        );
        this.nearestStore = nearestStore;
        if (Objects.isNull(nearestStore)) {
            throw ExceptionUtils.buildGenericException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.NOT_AVAILABLE_LAST_MOVEMENT,
                    "There is no suitable store nearby!!"
            );
        }
    }

    private void validateLastMovement(LocationRequestDTO locationRequestDTO) {
        if (!isAvailableLastMovement(locationRequestDTO.getCourierId())) {
            throw ExceptionUtils.buildGenericException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.NOT_AVAILABLE_LAST_MOVEMENT,
                    "Courier last movement is not available !!"
            );
        }
    }

    private void updateExistingCourierMovement(LocationRequestDTO locationRequestDTO, CourierMovementHistory courierMovementHistory) {
        saveCourierMovementToDatabase(courierMovementHistory, nearestStore);
        logger.info("updated courier history {}", locationRequestDTO.getCourierId());
    }

    private void createNewCourierMovement(Long courierId) {
        var courier = courierDAO.getCourierById(courierId).orElseGet(null);
        courierMovementHistoryDAO.saveCourierMovementHistory(CourierMovementHistory.builder()
                .courier(courier)
                .date(LocalDateTime.now())
                .storeId(null).build());
        Manager.entranceNotify(Long.toString(courier.getCourierId()));
        logger.info("Added courier history {}" , courierId);
    }

    private void saveCourierMovementToDatabase(CourierMovementHistory courierMovementHistory, StoreDTO nearestStore) {
        Store store = storeMapper.toEntity(nearestStore);
        courierMovementHistory.setStoreId(store.getId());
        courierMovementHistory.setDate(LocalDateTime.now());
        courierMovementHistoryDAO.saveCourierMovementHistory(courierMovementHistory);
    }

    private boolean isAvailableLastMovement(Long courierId) {
        var lastEntranceTime = courierMovementHistoryDAO.getCourierMovementHistory(courierId);
        return lastEntranceTime.map(courierMovementHistory -> courierMovementHistory.getDate().isBefore(LocalDateTime.now().minusMinutes(1))).orElse(true);
    }

    private StoreDTO findNearestStore(double lat, double lng) {
        var allStore = storeDAO.getAllStore();
        var allStoreDTO = storeMapper.toDTOList(allStore);
        return allStoreDTO.stream()
                .filter(store -> calculatorService.calculateDistance(lat, lng, store.getLat(), store.getLng()) <= 100)
                .min(Comparator.comparingDouble(store -> calculatorService.calculateDistance(lat, lng, store.getLat(), store.getLng())))
                .orElse(null);
    }
}
