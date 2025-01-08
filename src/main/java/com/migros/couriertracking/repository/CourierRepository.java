package com.migros.couriertracking.repository;

import com.migros.couriertracking.entity.Courier;
import com.migros.couriertracking.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {
    Courier findCourierByCourierId(String courierId);
}
