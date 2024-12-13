package com.edsh.is_lab1.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "IS_ImportHistory")
public class ImportHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    @Enumerated(EnumType.STRING)
    private Status status = Status.FAILED;
    private int entities = 0;
    private LocalDateTime importedAt = LocalDateTime.now();

    @ManyToOne
    private User importedBy; // Кто изменил

    public enum Status {
        COMMITTED, FAILED
    }

}
