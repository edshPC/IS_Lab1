package com.edsh.is_lab1.model;

import com.edsh.is_lab1.entity.ChangeListenedEntity;
import com.edsh.is_lab1.entity.User;
import jakarta.validation.Valid;
import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "IS_Dragon")
@Repository
public class Dragon extends ChangeListenedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1, message = "ID должен быть больше 0")
    private Integer id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @NotNull
    @NotBlank(message = "Строка имени не может быть пустой")
    private String name; // Поле не может быть null, Строка не может быть пустой
    @NotNull
    @Valid
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, optional = false)
    private Coordinates coordinates; // Поле не может быть null
    @NotNull
    @Column(updatable = false)
    private LocalDateTime creationDate = LocalDateTime.now(); // Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @NotNull
    @Valid
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, optional = false)
    private DragonCave cave; // Поле не может быть null
    @Valid
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private Person killer; // Поле может быть null
    @NotNull
    @Min(value = 1, message = "Возраст должен быть больше 0")
    private Long age; // Значение поля должно быть больше 0, Поле не может быть null
    @NotNull
    private Color color; // Поле не может быть null
    private DragonType type; // Поле может быть null
    @NotNull
    private DragonCharacter character; // Поле не может быть null
    @Valid
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    private DragonHead head; // Поле может быть null

    @ManyToOne
    private User owner;

    @Override
    public Long getListenedId() {
        return id == null ? null : id.longValue();
    }
}
