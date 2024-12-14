package com.edsh.is_lab1.controller;

import com.edsh.is_lab1.configuration.UserAuthProvider;
import com.edsh.is_lab1.dto.AuthRequest;
import com.edsh.is_lab1.dto.AuthResponse;
import com.edsh.is_lab1.entity.User;
import com.edsh.is_lab1.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserAuthProvider userAuthProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthRequest authRequest) {
        var user = userService.login(authRequest);
        var token = userAuthProvider.createToken(authRequest.getUsername());
        return new AuthResponse(token, user).asResponseEntity();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthRequest authRequest) {
        var user = userService.register(authRequest);
        var token = userAuthProvider.createToken(authRequest.getUsername());
        return new AuthResponse(token, user).asResponseEntity();
    }

    @GetMapping("/check_auth")
    public ResponseEntity<?> checkAuth(@AuthenticationPrincipal User user) {
        var response = new AuthResponse();
        if (user != null) response.setLogged_as(user);
        return response.asResponseEntity();
    }

}
