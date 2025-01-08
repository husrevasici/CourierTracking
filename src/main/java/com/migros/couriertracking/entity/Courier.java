package com.migros.couriertracking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Courier {
    @Id
    private String courierId;
    private double totalDistance = 0.0;
    private double lastLat;
    private double lastLng;
    private LocalDateTime lastEntryTime;
}