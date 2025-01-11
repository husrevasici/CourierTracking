package com.migros.couriertracking.manager;

import com.migros.couriertracking.dto.ErrorCode;
import com.migros.couriertracking.dto.LocationRequestDTO;
import com.migros.couriertracking.dto.StoreDTO;
import com.migros.couriertracking.entity.CourierMovementHistory;
import com.migros.couriertracking.entity.Store;
import com.migros.couriertracking.exception.GenericException;
import com.migros.couriertracking.mapper.StoreMapper;
import com.migros.couriertracking.repository.dao.CourierDAO;
import com.migros.couriertracking.repository.dao.CourierMovementHistoryDAO;
import com.migros.couriertracking.repository.dao.StoreDAO;
import com.migros.couriertracking.service.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

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


    @Override
    public void execute(LocationRequestDTO locationRequestDTO) {
        try {
            var courierId = locationRequestDTO.getCourierId();
            var courierMovementHistory = courierMovementHistoryDAO.getCourierMovementHistory(courierId);

            if (courierId.equalsIgnoreCase(courierMovementHistory.getCourier().getCourierId())) {
                updateExistingCourierMovement(locationRequestDTO, courierMovementHistory);
            } else {
                createNewCourierMovement(courierId);
            }
        } catch (Exception e) {
            throw GenericException.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .errorCode(ErrorCode.COURIER_HISTORY_SAVING_ERROR)
                    .errorMessage("courier history saving error !!")
                    .build();
        }
    }

    private void createNewCourierMovement(String courierId) {
        var courier = courierDAO.getCourierById(courierId).orElseGet(null);

        courierMovementHistoryDAO.saveCourierMovementHistory(CourierMovementHistory.builder()
                .courier(courier)
                .date(LocalDateTime.now())
                .storeId(null).build());
        Manager.entranceNotify(courier.getCourierId());
        logger.info("Added courier history {}" , courierId);
    }

    private void updateExistingCourierMovement(LocationRequestDTO locationRequestDTO, CourierMovementHistory courierMovementHistory) {
        var courierId = locationRequestDTO.getCourierId();
        var nearestStore = findNearestStore(locationRequestDTO.getLocation().getLatitude(), locationRequestDTO.getLocation().getLongitude());

        if (!isAvailableLastMovement(courierId)) {
            throw GenericException.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .errorCode(ErrorCode.NOT_AVAILABLE_LAST_MOVEMENT)
                    .errorMessage("courier last movement is not available !!")
                    .build();
        }
        saveCourierMovementToDatabase(courierMovementHistory, nearestStore);
        logger.info("CourierMovementHistoryDAO: CourierMovementHistory added {}", courierId);
    }

    private void saveCourierMovementToDatabase(CourierMovementHistory courierMovementHistory, StoreDTO nearestStore) {
        Store store = storeMapper.toEntity(nearestStore);
        courierMovementHistory.setStoreId(store.getId());
        courierMovementHistory.setDate(LocalDateTime.now());
        courierMovementHistoryDAO.saveCourierMovementHistory(courierMovementHistory);
    }

    private boolean isAvailableLastMovement(String courierId) {
        var lastEntranceTime = courierMovementHistoryDAO.getCourierMovementHistory(courierId).getDate();
        return Objects.nonNull(lastEntranceTime) && !lastEntranceTime.isBefore(LocalDateTime.now().minusMinutes(1));
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
