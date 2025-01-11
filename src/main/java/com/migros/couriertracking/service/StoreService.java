package com.migros.couriertracking.service;

import com.migros.couriertracking.mapper.StoreMapper;
import com.migros.couriertracking.dto.StoreDTO;
import com.migros.couriertracking.repository.dao.StoreDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StoreService {

    @Autowired
    StoreDAO storeDAO;
    @Autowired
    StoreMapper storeMapper;

    public List<StoreDTO> getAllStores() {
        var allStore = storeDAO.getAllStore();
        return storeMapper.toDTOList(allStore);
    }
}