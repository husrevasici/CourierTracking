package com.migros.couriertracking.util;

import com.migros.couriertracking.dto.ResponseDTO;
import com.migros.couriertracking.exception.GenericException;
import org.springframework.http.HttpStatus;

import java.util.Objects;

public class ResponseUtils {

    private ResponseUtils() {
        // Private constructor
    }

    public static ResponseDTO createSuccessResponse(Object data, String message) {
        return ResponseDTO.builder()
                .status(HttpStatus.OK)
                .data(Objects.isNull(data) ? "" : data)
                .message(message)
                .build();
    }

    public static ResponseDTO createErrorResponse(GenericException e) {
        return ResponseDTO.builder()
                .errorCode(e.getErrorCode())
                .message(e.getErrorMessage())
                .status(e.getHttpStatus())
                .build();
    }


}
