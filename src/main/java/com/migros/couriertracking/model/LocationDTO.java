package com.migros.couriertracking.model;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
public class LocationDTO {
    private LocalDateTime timestamp;
    private double lat;
    private double lng;

}