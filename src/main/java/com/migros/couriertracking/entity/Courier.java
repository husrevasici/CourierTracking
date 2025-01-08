package com.migros.couriertracking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "courier")

public class Courier {
    @Id
    private String courierId;

    private String nameAndSurname;

    @OneToMany(mappedBy = "courier", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourierLocation> courierLocations;

    @OneToOne(mappedBy = "courier", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private CourierMovementHistory lastMovement;

    public String getCourierId() {
        return courierId;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }

    public String getNameAndSurname() {
        return nameAndSurname;
    }

    public void setNameAndSurname(String nameAndSurname) {
        this.nameAndSurname = nameAndSurname;
    }

    public List<CourierLocation> getCourierLocations() {
        return courierLocations;
    }

    public void setCourierLocations(List<CourierLocation> courierLocations) {
        this.courierLocations = courierLocations;
    }

    public CourierMovementHistory getLastMovement() {
        return lastMovement;
    }

    public void setLastMovement(CourierMovementHistory lastMovement) {
        this.lastMovement = lastMovement;
    }
}


