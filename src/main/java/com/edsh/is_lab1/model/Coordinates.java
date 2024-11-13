package com.edsh.is_lab1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Data
@Entity
@Table(name = "IS_Coordinates")
public class Coordinates {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Max(value = 235, message = "Максимальное значение поля x: 235")
    private Long x; // Максимальное значение поля: 235, Поле не может быть null
    @NotNull
    @Max(value = 811, message = "Максимальное значение поля y: 811")
    private Long y; // Максимальное значение поля: 811, Поле не может быть null
}
