package com.edsh.is_lab1.exception;

import com.edsh.is_lab1.dto.SimpleResponse;
import jakarta.validation.ValidationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;

import java.util.stream.Collectors;

@ControllerAdvice
public class AppExceptionHandler {

    private final View error;

    public AppExceptionHandler(View error) {
        this.error = error;
    }

    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ResponseEntity<?> handleAppException(AppException e) {
        return SimpleResponse.error(e.getMessage(), e.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {
        var message = e.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return SimpleResponse.error(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> handleGlobalException(Exception e) {
        return SimpleResponse.error(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
