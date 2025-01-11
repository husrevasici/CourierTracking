package com.migros.couriertracking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@Setter
public class ResponseDTO {
    private HttpStatus status;
    private String message;
    private ErrorCode errorCode;
    private Object data;
}
