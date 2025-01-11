package com.migros.couriertracking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "courier")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Courier {
    @Id
    private String courierId;

    private String nameAndSurname;

    private Double totalDistance = 0.0;

    @OneToMany(mappedBy = "courier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourierLocation> courierLocations;

    @OneToOne(mappedBy = "courier", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private CourierMovementHistory lastMovement;

}


