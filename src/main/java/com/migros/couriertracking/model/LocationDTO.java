package com.migros.couriertracking.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LocationDTO {
    private LocalDateTime timestamp;
    private double lat;
    private double lng;

}