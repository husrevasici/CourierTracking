package com.migros.couriertracking.service;

import com.migros.couriertracking.repository.StoreRepository;
import com.migros.couriertracking.model.StoreDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<StoreDTO> getAllStores() {
/*
      return storeRepository.findAll()
                .stream()
                .map(store -> StoreDTO.builder()
                        .lng(store.getLng())
                        .lat(store.getLat())
                        .name(store.getName())
                        .build())
                .collect(Collectors.toList());

 */
 return null;
    }
}