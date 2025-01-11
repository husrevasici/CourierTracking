package com.migros.couriertracking.repository.dao;

import com.migros.couriertracking.entity.Courier;
import com.migros.couriertracking.repository.CourierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CourierDAO {

    @Autowired
    private CourierRepository courierRepository;

    public Optional<Courier> getCourierById(String courierId) {
        return Optional.ofNullable(courierRepository.findByCourierId(courierId));
    }

    public Courier courierSave(Courier courier) {
        return courierRepository.save(courier);
    }


}
