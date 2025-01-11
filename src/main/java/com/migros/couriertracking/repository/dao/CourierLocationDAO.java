package com.migros.couriertracking.repository.dao;

import com.migros.couriertracking.dto.ErrorCode;
import com.migros.couriertracking.entity.Courier;
import com.migros.couriertracking.entity.CourierLocation;
import com.migros.couriertracking.exception.GenericException;
import com.migros.couriertracking.repository.CourierLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class CourierLocationDAO {

    @Autowired
    private CourierLocationRepository courierLocationRepository;
    private static final String COURIER_LOCATION_IS_NOT_ADDED = "The courier's location was not recorded.";

    public void saveCourierLocation(Courier courier, double lat, double lng) {
        try {
            courierLocationRepository.save(CourierLocation.builder()
                    .courier(courier)
                    .latitude(lat)
                    .longitude(lng)
                    .timestamp(LocalDateTime.now())
                    .build());
        } catch (Exception e) {
            throw GenericException.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .errorCode(ErrorCode.COURIER_LOCATION_IS_NOT_ADDED)
                    .errorMessage(COURIER_LOCATION_IS_NOT_ADDED).build();
        }
    }

    public List<CourierLocation> getCourierLocations(String courierId) {
        return courierLocationRepository.findAllByCourier_CourierId(courierId);
    }
}
