package com.edsh.is_lab1.service;

import com.edsh.is_lab1.dto.LoginRequest;
import com.edsh.is_lab1.entity.User;
import com.edsh.is_lab1.exception.AppException;
import com.edsh.is_lab1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new AppException("User not found", HttpStatus.NOT_FOUND));
    }

    public User login(LoginRequest loginRequest) {
        User user = findByLogin(loginRequest.getLogin());
        if(passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return user;
        }
        throw new AppException("Wrong password", HttpStatus.BAD_REQUEST);
    }

    public User register(LoginRequest loginRequest) {
        if (userRepository.findByLogin(loginRequest.getLogin()).isPresent()) {
            throw new AppException("User already exists", HttpStatus.BAD_REQUEST);
        }
        User user = loginRequest.asUser(passwordEncoder);
        user.setPermission(User.Permission.USER);
        userRepository.save(user);
        return user;
    }

}
