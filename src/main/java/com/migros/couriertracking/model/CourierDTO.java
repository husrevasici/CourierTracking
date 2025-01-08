package com.migros.couriertracking.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourierDTO {
    private String courierId;
    private List<LocationDTO> locationDTOS = new ArrayList<>();
    private Map<Long, LocalDateTime> lastEntranceTimes = new HashMap<>();

    // Mağaza için en son giriş zamanını al
    public LocalDateTime getLastEntranceTime(StoreDTO store) {
        return lastEntranceTimes.get(store.getId());
    }

    // Mağaza için en son giriş zamanını ayarla
    public void setLastEntranceTime(StoreDTO store, LocalDateTime time) {
        lastEntranceTimes.put(store.getId(), time);
    }

}