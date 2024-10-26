package com.edsh.is_lab1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ObjectController {

    @GetMapping("/get")
    public ResponseEntity<?> get() {
        return ResponseEntity.ok("Ok");
    }

}
