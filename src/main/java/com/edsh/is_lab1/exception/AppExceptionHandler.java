package com.edsh.is_lab1.exception;

import com.edsh.is_lab1.dto.SimpleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ResponseEntity<?> handleAppException(AppException e) {
        return SimpleResponse.error(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {
        var message = e.getBindingResult().getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));
        return SimpleResponse.error(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<?> handleGlobalException(Throwable e) {
        //e.printStackTrace();
        while (e.getMessage() == null && e.getCause() != null) {
            e = e.getCause();
        }
        return SimpleResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
