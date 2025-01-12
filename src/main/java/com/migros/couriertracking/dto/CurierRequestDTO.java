package com.migros.couriertracking.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CurierRequestDTO {
    @NotNull(message = "Name and surname cannot be null")
    private String nameAndSurname;
    @NotNull(message = "Phone number cannot be null")
    private String phoneNumber;
    @NotNull(message = "Email cannot be null")
    private String email;

}
