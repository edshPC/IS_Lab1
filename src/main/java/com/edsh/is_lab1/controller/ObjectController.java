package com.edsh.is_lab1.controller;

import com.edsh.is_lab1.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ObjectController {

    @GetMapping("/get")
    public ResponseEntity<?> get(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok("user " + user.getLogin());
    }

}
