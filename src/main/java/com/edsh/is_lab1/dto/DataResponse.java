package com.edsh.is_lab1.dto;

import lombok.*;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
public class DataResponse<T> extends BaseResponse<DataResponse<T>> {
    private T data;

    public static <T> ResponseEntity<?> success(T data) {
        return new DataResponse<T>(data).asResponseEntity();
    }

}
