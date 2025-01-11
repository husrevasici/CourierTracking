package com.migros.couriertracking.dto;

import com.migros.couriertracking.validation.ValidCourierId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class LocationRequestDTO {

    @ValidCourierId
    @NotBlank(message = "Courier ID cannot be blank")
    private String courierId;

    @NotNull(message = "Location cannot be null")
    private LocationDTO location;

    @NotNull(message = "Time field cannot be null")
    private LocalDateTime time;
}
