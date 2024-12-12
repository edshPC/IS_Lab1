package com.edsh.is_lab1.dto;

import com.edsh.is_lab1.entity.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class AuthRequest {
    private String login;
    private String password;

    public User asUser(PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(login);
        user.setPassword(passwordEncoder.encode(password));
        return user;
    }

}

