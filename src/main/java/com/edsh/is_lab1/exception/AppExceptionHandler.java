package com.edsh.is_lab1.exception;

import com.edsh.is_lab1.dto.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {AppException.class})
    @ResponseBody
    public ResponseEntity<Response> handle(AppException e) {
        return ResponseEntity.status(e.getStatus())
                .body(Response.builder()
                        .message(e.getMessage())
                        .success(false)
                        .build());
    }

}
