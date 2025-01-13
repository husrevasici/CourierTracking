package com.migros.couriertracking.repository.dao;

import com.migros.couriertracking.entity.Store;
import com.migros.couriertracking.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StoreDAO {
    private final StoreRepository storeRepository;

    public StoreDAO(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<Store> getAllStore() {
        return storeRepository.findAll();
    }
}


