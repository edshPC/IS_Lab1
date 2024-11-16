package com.edsh.is_lab1.service;

import com.edsh.is_lab1.dto.AuthRequest;
import com.edsh.is_lab1.entity.User;
import com.edsh.is_lab1.exception.AppException;
import com.edsh.is_lab1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public User login(AuthRequest authRequest) {
        User user = findByLogin(authRequest.getLogin());
        if(passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            return user;
        }
        throw new AppException("Wrong password", HttpStatus.BAD_REQUEST);
    }

    public User register(AuthRequest authRequest) {
        if (userRepository.findByLogin(authRequest.getLogin()).isPresent()) {
            throw new AppException("User already exists");
        }
        if (authRequest.getPassword().length() < 4) {
            throw new AppException("Password must be at least 4 characters");
        }
        User user = authRequest.asUser(passwordEncoder);
        user.setPermission(User.Permission.USER);
        userRepository.save(user);
        return user;
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
