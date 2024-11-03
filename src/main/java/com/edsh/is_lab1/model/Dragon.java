package com.edsh.is_lab1.model;

import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "IS_Dragon")
public class Dragon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1, message = "ID должен быть больше 0")
    private Integer id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @NotNull
    @NotBlank(message = "Строка имени не может быть пустой")
    private String name; // Поле не может быть null, Строка не может быть пустой
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Coordinates coordinates; // Поле не может быть null
    @NotNull
    @Column(updatable = false)
    private LocalDateTime creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private DragonCave cave; // Поле не может быть null
    @ManyToOne(cascade = CascadeType.ALL)
    private Person killer; // Поле может быть null
    @NotNull
    @Min(value = 1, message = "Возраст должен быть больше 0")
    private Long age; // Значение поля должно быть больше 0, Поле не может быть null
    @NotNull
    private Color color; // Поле не может быть null
    private DragonType type; // Поле может быть null
    @NotNull
    private DragonCharacter character; // Поле не может быть null
    @ManyToOne(cascade = CascadeType.ALL)
    private DragonHead head; // Поле может быть null
}
