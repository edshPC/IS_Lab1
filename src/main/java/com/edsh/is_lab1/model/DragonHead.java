package com.edsh.is_lab1.model;

import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "IS_DragonHead")
public class DragonHead implements Appliable<DragonHead> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long size;
    @NotNull
    private Double eyesCount; // Поле не может быть null
    private double toothCount;

    @Override
    public void applyDataFrom(DragonHead other) {
        size = other.size;
        eyesCount = other.eyesCount;
        toothCount = other.toothCount;
    }
}
