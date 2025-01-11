package com.migros.couriertracking.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "store")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double lat;
    private double lng;

    @Builder
    public static Store buildStore(String name, double lat, double lng) {
        return new Store(null, name, lat, lng);
    }
}
