package com.edsh.is_lab1.dto;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@NoArgsConstructor
public class SimpleResponse extends BaseResponse<SimpleResponse> {

    public SimpleResponse(String message, boolean success) {
        super(message, success);
    }

    public static ResponseEntity<?> success() {
        return new SimpleResponse().asResponseEntity();
    }

    public static ResponseEntity<?> error(String message) {
        return new SimpleResponse(message, false).asResponseEntity(HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> error(String message, HttpStatus status) {
        return new SimpleResponse(message, false).asResponseEntity(status);
    }

}
