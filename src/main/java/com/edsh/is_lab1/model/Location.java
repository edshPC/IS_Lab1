package com.edsh.is_lab1.model;

import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Data
@Entity
@Table(name = "IS_Location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float x;
    private long y;
    private float z;
    @Size(max = 401, message = "Длина строки не должна превышать 401 символ")
    private String name; // Поле может быть null

}
