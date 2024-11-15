package com.edsh.is_lab1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import jakarta.persistence.*;

import java.util.List;

@Data
@Entity
@Table(name = "IS_DragonCave")
public class DragonCave {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double depth; // Поле может быть null
    @DecimalMin(value = "0.01", message = "Количество сокровищ должно быть больше 0")
    private double numberOfTreasures; // Значение поля должно быть больше 0

}
