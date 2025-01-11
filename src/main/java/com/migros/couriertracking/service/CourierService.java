package com.migros.couriertracking.service;

import com.migros.couriertracking.dto.LocationRequestDTO;
import com.migros.couriertracking.dto.ResponseDTO;
import com.migros.couriertracking.exception.GenericException;
import com.migros.couriertracking.manager.CourierManager;
import com.migros.couriertracking.manager.TravelerManager;
import com.migros.couriertracking.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourierService {

    @Autowired
    private CourierManager courierManager;
    @Autowired
    private TravelerManager travelerManager;

    private static final String LOCATION_ADDED_SUCCESSFULLY = "Location added successfully";
    private static final String TOTAL_TRAVELER_DISTANCE = "Total traveler distance calculated";

    public ResponseDTO addLocation(LocationRequestDTO locationRequestDTO) {
        try {
            courierManager.execute(locationRequestDTO);
            travelerManager.execute(locationRequestDTO);
            return ResponseUtils.createSuccessResponse(null, LOCATION_ADDED_SUCCESSFULLY);
        } catch (GenericException e) {
            return ResponseUtils.createErrorResponse(e);
        }
    }

    public ResponseDTO getTotalTravelDistance(String courierId) {
        try {
            return ResponseUtils.createSuccessResponse(courierManager.getTotalTravelDistance(courierId), TOTAL_TRAVELER_DISTANCE);
        } catch (GenericException e) {
            return ResponseUtils.createErrorResponse(e);
        }
    }
}