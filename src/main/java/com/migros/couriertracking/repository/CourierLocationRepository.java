package com.migros.couriertracking.repository;

import com.migros.couriertracking.entity.CourierLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourierLocationRepository extends JpaRepository<CourierLocation, Long> {
    List<CourierLocation> findAllByCourier_CourierId(String courierId);
}
