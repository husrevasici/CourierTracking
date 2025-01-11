package com.migros.couriertracking.repository.dao;

import com.migros.couriertracking.entity.CourierMovementHistory;
import com.migros.couriertracking.repository.CourierMovementHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CourierMovementHistoryDAO {

    @Autowired
    private CourierMovementHistoryRepository courierMovementHistoryRepository;

    public CourierMovementHistory getCourierMovementHistory(String courierId) {
        return courierMovementHistoryRepository.findByCourier_CourierId(courierId);
    }

    public void saveCourierMovementHistory(CourierMovementHistory courierMovementHistory) {
        courierMovementHistoryRepository.save(courierMovementHistory);
    }

}
