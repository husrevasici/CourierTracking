package com.migros.couriertracking.controller;

import com.migros.couriertracking.service.CourierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/couriers")
public class CourierController {
    private final CourierService courierService;

    @Autowired
    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    @PostMapping("/{courierId}/location")
    public ResponseEntity<Void> updateLocation(@PathVariable String courierId,
                                               @RequestParam double lat,
                                               @RequestParam double lng) {
        courierService.createLocation(courierId, lat, lng);
        return ResponseEntity.ok().build();

    }
/*
    @GetMapping("/{courierId}/distance")
    public ResponseEntity<Double> getTotalTravelDistance(@PathVariable String courierId) {

        return ResponseEntity.ok(courierService.getTotalTravelDistance(courierId));

    }*/
}
