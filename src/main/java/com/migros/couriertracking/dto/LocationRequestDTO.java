package com.migros.couriertracking.dto;

import com.migros.couriertracking.validation.ValidCourierId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequestDTO {

    @ValidCourierId
    private Long courierId;

    @NotNull(message = "Location cannot be null")
    private LocationDTO location;

    @NotNull(message = "Time field cannot be null")
    private LocalDateTime time;
}
