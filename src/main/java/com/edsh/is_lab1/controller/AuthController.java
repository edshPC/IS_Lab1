package com.edsh.is_lab1.controller;

import com.edsh.is_lab1.configuration.UserAuthProvider;
import com.edsh.is_lab1.dto.AuthRequest;
import com.edsh.is_lab1.dto.AuthResponse;
import com.edsh.is_lab1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest authRequest) {
        var User = userService.login(authRequest);
        var token = userAuthProvider.createToken(authRequest.getLogin());
        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .build());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthRequest authRequest) {
        var User = userService.register(authRequest);
        var token = userAuthProvider.createToken(authRequest.getLogin());
        return ResponseEntity.ok(AuthResponse.builder()
                .token(token)
                .build());
    }
}
