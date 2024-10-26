package com.edsh.is_lab1.controller;

import com.edsh.is_lab1.configuration.UserAuthProvider;
import com.edsh.is_lab1.dto.LoginRequest;
import com.edsh.is_lab1.dto.Response;
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
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        var User = userService.login(loginRequest);
        return ResponseEntity.ok(Response.builder()
                .token(userAuthProvider.createToken(loginRequest.getLogin())));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody LoginRequest loginRequest) {
        var User = userService.register(loginRequest);
        return ResponseEntity.ok(Response.builder()
                .token(userAuthProvider.createToken(loginRequest.getLogin())));
    }
}
