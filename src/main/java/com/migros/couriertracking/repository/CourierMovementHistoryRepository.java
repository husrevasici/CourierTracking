package com.migros.couriertracking.repository;


import com.migros.couriertracking.entity.CourierMovementHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface CourierMovementHistoryRepository extends JpaRepository<CourierMovementHistory, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE CourierMovementHistory c SET c.date = :newDate , c.storeId = :newStoreId WHERE c.courier.courierId = :courierId")
    void updateCourierId(@Param("newDate") LocalDateTime newDate, @Param("newStoreId") Long newStoreId, @Param("courierId") String courierId);

    CourierMovementHistory findByCourier_CourierId(String courierCourierId);

}
