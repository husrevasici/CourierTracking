package com.migros.couriertracking.repository.dao;

import com.migros.couriertracking.entity.Courier;
import com.migros.couriertracking.repository.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CourierDAO {

    private final CourierRepository courierRepository;

    public CourierDAO(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    public Optional<Courier> getCourierById(Long courierId) {
        return Optional.ofNullable(courierRepository.findByCourierId(courierId));
    }

    public void courierSave(Courier courier) {
        courierRepository.save(courier);
    }

}
