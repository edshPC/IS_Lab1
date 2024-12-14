package com.edsh.is_lab1.dto;

import com.edsh.is_lab1.entity.User;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
public class AuthRequest {
    private String username;
    private String password;

    public User asUser(PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return user;
    }

}

