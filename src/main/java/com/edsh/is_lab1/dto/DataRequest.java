package com.edsh.is_lab1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataRequest<T> {
    T data;
    boolean isNew;
}
