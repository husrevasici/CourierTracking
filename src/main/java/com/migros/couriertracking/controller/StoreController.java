package com.migros.couriertracking.controller;

import com.migros.couriertracking.model.StoreDTO;
import com.migros.couriertracking.service.StoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/stores")
    public List<StoreDTO> getStores() {
        return storeService.getAllStores();
    }
}