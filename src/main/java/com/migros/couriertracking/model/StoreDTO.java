package com.migros.couriertracking.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class StoreDTO {
    private Long id;
    private String name;
    private double lat;
    private double lng;
}