package com.migros.couriertracking.controller;

import com.migros.couriertracking.constant.ApiUrls;
import com.migros.couriertracking.dto.StoreDTO;
import com.migros.couriertracking.service.StoreService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiUrls.STORES)
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/")
    public List<StoreDTO> getStores() {
        return storeService.getAllStores();
    }
}