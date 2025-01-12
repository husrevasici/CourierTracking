package com.migros.couriertracking.manager;

import com.migros.couriertracking.dto.CurierRequestDTO;
import com.migros.couriertracking.dto.ErrorCode;
import com.migros.couriertracking.dto.LocationRequestDTO;
import com.migros.couriertracking.entity.Courier;
import com.migros.couriertracking.exception.GenericException;
import com.migros.couriertracking.repository.dao.CourierDAO;
import com.migros.couriertracking.repository.dao.CourierLocationDAO;
import com.migros.couriertracking.service.CalculatorService;
import com.migros.couriertracking.util.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.stream.IntStream;

@Component
public class CourierManager extends Command<LocationRequestDTO>{
    @Autowired
    private CourierDAO courierDAO;
    @Autowired
    private CourierLocationDAO courierLocationDAO;
    @Autowired
    private CalculatorService calculatorService;

    private static Logger logger = LoggerFactory.getLogger(CourierManager.class);
    private static final String COURIER_NOT_FOUND_MESSAGE = "Courier not found!";
    private static final String UNEXPECTED_ERROR_OCCURED = "an unexpected error occurred while executing location add";
    private static final String TOTAL_TRAVEL_DISTANCE_CALCULATE_ERROR = "error received when calculating total travel distance";
    private static final String COURIER_LOCATION_ADDED = "Courier location added";


    @Override
    public void execute(LocationRequestDTO locationRequestDTO) {
        try {
            Courier courier = courierDAO
                    .getCourierById(locationRequestDTO.getCourierId())
                    .orElseThrow(() -> ExceptionUtils.buildGenericException(
                            HttpStatus.NOT_FOUND,
                            ErrorCode.COURIER_NOT_FOUND,
                            COURIER_NOT_FOUND_MESSAGE
                    ));
            courierLocationDAO.saveCourierLocation(courier, locationRequestDTO.getLocation().getLatitude(), locationRequestDTO.getLocation().getLongitude());
            logger.info(COURIER_LOCATION_ADDED);
        } catch (GenericException e) {
            logger.error(UNEXPECTED_ERROR_OCCURED, e);
            throw e;
        } catch (Exception e) {
            logger.error(UNEXPECTED_ERROR_OCCURED, e);
            throw ExceptionUtils.buildGenericException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.UNEXPECTED_ERROR_OCCURED,
                    UNEXPECTED_ERROR_OCCURED
            );
        }
    }


    public Double getTotalTravelDistance(Long courierId) {
        try {
            var locations = courierLocationDAO.getCourierLocations(courierId);
            return IntStream.range(0, locations.size() - 1)
                    .mapToDouble(i -> calculatorService.calculateDistance(
                            locations.get(i).getLatitude(),
                            locations.get(i).getLongitude(),
                            locations.get(i + 1).getLatitude(),
                            locations.get(i + 1).getLongitude()
                    ))
                    .sum();
        } catch (Exception e) {
            throw ExceptionUtils.buildGenericException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    ErrorCode.TOTAL_TRAVEL_DISTANCE_CALCULATE_ERROR,
                    TOTAL_TRAVEL_DISTANCE_CALCULATE_ERROR
            );
        }
    }

    public void createCourier(CurierRequestDTO locationRequestDTO) {
        Courier build = Courier.builder()
                .nameAndSurname(locationRequestDTO.getNameAndSurname())
                .phoneNumber(locationRequestDTO.getPhoneNumber())
                .email(locationRequestDTO.getEmail())
                .build();
        courierDAO.courierSave(build);
        logger.info(locationRequestDTO.getNameAndSurname() + " courier created");
    }
}
