package com.edsh.is_lab1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "IS_User")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String login;
    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private Permission permission = Permission.ANY;

    public enum Permission {
        ANY,
        USER,
        ADMIN
    }
}


