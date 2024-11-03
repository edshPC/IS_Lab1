package com.edsh.is_lab1.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "IS_Person")
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotBlank
    private String name; //Поле не может быть null, Строка не может быть пустой
    @NotNull
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле может быть null
    @ManyToOne(cascade = CascadeType.ALL)
    private Location location; //Поле не может быть null
    @NotNull
    @Min(value = 1, message = "Рост быть больше 0")
    private Long height; //Поле не может быть null, Значение поля должно быть больше 0
    @DecimalMin(value = "0.01", message = "Вес должен быть больше 0")
    private float weight; //Значение поля должно быть больше 0
    @Size(min = 10, message = "Длина паспорта должна быть не меньше 10 символов")
    @NotNull
    private String passportID; //Длина строки должна быть не меньше 10, Поле не может быть null
    private Country nationality; //Поле может быть null

}
