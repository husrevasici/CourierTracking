package com.migros.couriertracking.util;

import com.migros.couriertracking.dto.ErrorCode;
import com.migros.couriertracking.exception.GenericException;
import org.springframework.http.HttpStatus;

public class ExceptionUtils {
    private ExceptionUtils() {
    // private constructor
    }

    public static GenericException buildGenericException(HttpStatus httpStatus, ErrorCode errorCode, String errorMessage) {
        return GenericException.builder()
                .httpStatus(httpStatus)
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build();
    }
}
