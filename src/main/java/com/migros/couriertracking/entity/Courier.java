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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courierId;
    private String nameAndSurname;
    private String phoneNumber;
    private String email;

    @OneToMany(mappedBy = "courier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourierLocation> courierLocations;

    @OneToOne(mappedBy = "courier", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private CourierMovementHistory lastMovement;

}


