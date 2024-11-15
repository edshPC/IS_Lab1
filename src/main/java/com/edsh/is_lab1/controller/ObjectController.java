package com.edsh.is_lab1.controller;

import com.edsh.is_lab1.dto.DataResponse;
import com.edsh.is_lab1.dto.SimpleResponse;
import com.edsh.is_lab1.entity.User;
import com.edsh.is_lab1.model.Dragon;
import com.edsh.is_lab1.service.DragonService;
import com.edsh.is_lab1.service.PersonService;
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
    private final PersonService personService;

    @GetMapping("/public/get_all_dragons")
    public ResponseEntity<?> getDragons() {
        return DataResponse.success(dragonService.getAllDragons());
    }

    @GetMapping("/get_all_killers")
    public ResponseEntity<?> getKillers() {
        return DataResponse.success(personService.getAllPeople());
    }

    @PostMapping("/add_dragon")
    public ResponseEntity<?> addDragon(@RequestBody @Valid Dragon dragon, @AuthenticationPrincipal User user) {
        dragonService.addDragon(dragon, user);
        return SimpleResponse.success("Дракон успешно добавлен");
    }

    @PostMapping("/update_dragon")
    public ResponseEntity<?> updateDragon(@RequestBody @Valid Dragon dragon, @AuthenticationPrincipal User user) {
        dragonService.checkDragonOwner(dragon, user);
        dragonService.updateDragon(dragon);
        return SimpleResponse.success("Дракон успешно обновлён");
    }

    @PostMapping("/remove_dragon")
    public ResponseEntity<?> removeDragon(@RequestBody Dragon dragon, @AuthenticationPrincipal User user) {
        dragonService.checkDragonOwner(dragon, user);
        dragonService.removeDragon(dragon);
        return SimpleResponse.success("Дракон успешно удалён");
    }

}
