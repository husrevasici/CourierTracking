package com.migros.couriertracking.controller;

import com.migros.couriertracking.constant.ApiUrls;
import com.migros.couriertracking.dto.LocationRequestDTO;
import com.migros.couriertracking.dto.ResponseDTO;
import com.migros.couriertracking.service.CourierService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiUrls.COURIERS)
public class CourierController {

    private final CourierService courierService;

    @Autowired
    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    @PostMapping(ApiUrls.ADD_COURIER_LOCATION_PATH)
    public ResponseDTO addLocation(@Valid @RequestBody LocationRequestDTO locationRequestDTO) {
        return courierService.addLocation(locationRequestDTO);
    }

    @GetMapping(ApiUrls.COURIER_TOTAL_DISTANCE_PATH)
    public ResponseDTO getTotalTravelDistance(@PathVariable String courierId) {
        return courierService.getTotalTravelDistance(courierId);
    }
}
