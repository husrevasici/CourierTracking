package com.migros.couriertracking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CurierRequestDTO {
    @NotNull(message = "Name and surname cannot be null")
    private String nameAndSurname;
    @NotNull(message = "Phone number cannot be null")
    private String phoneNumber;
    @NotNull(message = "Email cannot be null")
    private String email;

}
