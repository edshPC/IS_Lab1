package com.edsh.is_lab1.dto;

import com.edsh.is_lab1.entity.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class LoginRequest {
    private String login;
    private String password;

    public User asUser(PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        return user;
    }

}

