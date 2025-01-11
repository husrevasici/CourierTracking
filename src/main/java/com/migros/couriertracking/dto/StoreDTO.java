package com.migros.couriertracking.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreDTO {
    private Long id;
    private String name;
    private double lat;
    private double lng;

    public Long getId() {
        return id;
    }
}