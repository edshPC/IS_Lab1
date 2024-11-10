package com.edsh.is_lab1.controller;

import com.edsh.is_lab1.dto.DataResponse;
import com.edsh.is_lab1.dto.SimpleResponse;
import com.edsh.is_lab1.entity.User;
import com.edsh.is_lab1.model.Dragon;
import com.edsh.is_lab1.service.DragonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ObjectController {

    private final DragonService dragonService;

    @GetMapping("/public/get_all_dragons")
    public ResponseEntity<?> get() {
        return DataResponse.success(dragonService.getAllDragons());
    }

    @PostMapping("/add_dragon")
    public ResponseEntity<?> add(@RequestBody @Valid Dragon dragon, @AuthenticationPrincipal User user) {
        dragonService.addDragon(dragon);
        return SimpleResponse.success();
    }

    @PostMapping("/update_dragon")
    public ResponseEntity<?> update(@RequestBody @Valid Dragon dragon, @AuthenticationPrincipal User user) {
        dragonService.updateDragon(dragon);
        return SimpleResponse.success();
    }

    @PostMapping("/remove_dragon")
    public ResponseEntity<?> remove(@RequestBody Dragon dragon, @AuthenticationPrincipal User user) {
        dragonService.removeDragon(dragon);
        return SimpleResponse.success();
    }

}
