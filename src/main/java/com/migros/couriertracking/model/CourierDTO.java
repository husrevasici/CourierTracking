package com.migros.couriertracking.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    public void setLocationDTOS(List<LocationDTO> locationDTOS) {
        this.locationDTOS = locationDTOS;
    }

    public void setLastEntranceTimes(Map<Long, LocalDateTime> lastEntranceTimes) {
        this.lastEntranceTimes = lastEntranceTimes;
    }

    public String getCourierId() {
        return courierId;
    }

    public List<LocationDTO> getLocationDTOS() {
        return locationDTOS;
    }

    public Map<Long, LocalDateTime> getLastEntranceTimes() {
        return lastEntranceTimes;
    }


}