package com.migros.couriertracking.exception;

import com.migros.couriertracking.dto.ErrorCode;
import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class GenericException extends RuntimeException{
    private HttpStatus httpStatus;
    private ErrorCode errorCode;
    private String errorMessage;

    @Builder
    public static GenericException buildGenericException(HttpStatus httpStatus, ErrorCode errorCode, String errorMessage) {
        return new GenericException(httpStatus, errorCode, errorMessage);
    }
}