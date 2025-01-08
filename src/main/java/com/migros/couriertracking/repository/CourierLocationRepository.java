package com.migros.couriertracking.repository;

import com.migros.couriertracking.entity.Courier;
import com.migros.couriertracking.entity.CourierLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourierLocationRepository extends JpaRepository<CourierLocation, Long> {
}
