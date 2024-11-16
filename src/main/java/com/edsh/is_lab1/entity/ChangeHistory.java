package com.edsh.is_lab1.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "IS_ChangeHistory")
public class ChangeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String entity; // JSON

    private String entityName;
    private Long entityId;
    @Enumerated(EnumType.STRING)
    private Action action;
    private LocalDateTime changedAt = LocalDateTime.now();

    @ManyToOne
    private User changedBy; // Кто изменил

    enum Action {
        CREATE, UPDATE, DELETE
    }

}
