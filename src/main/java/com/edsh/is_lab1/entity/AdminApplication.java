package com.edsh.is_lab1.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "IS_AdminApplication",
        uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@NoArgsConstructor
public class AdminApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(optional = false)
    @JoinColumn(name = "username", nullable = false)
    private User user;
    private LocalDateTime creationDate = LocalDateTime.now();
}
