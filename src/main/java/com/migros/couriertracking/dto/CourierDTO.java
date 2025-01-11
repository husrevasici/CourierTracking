package com.migros.couriertracking.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CourierDTO {
    private String courierId;
    private List<LocationDTO> locationDTOS = new ArrayList<>();
    private Map<Long, LocalDateTime> lastEntranceTimes = new HashMap<>();
}