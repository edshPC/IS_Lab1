package com.edsh.is_lab1.controller;

import com.edsh.is_lab1.dto.DataResponse;
import com.edsh.is_lab1.dto.SimpleResponse;
import com.edsh.is_lab1.model.Person;
import com.edsh.is_lab1.service.PersonService;
import com.edsh.is_lab1.service.SpecialService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SpecialController {

    private final SpecialService specialService;
    private final PersonService personService;

    @GetMapping("/average-age")
    public ResponseEntity<?> averageAge() {
        return DataResponse.success(specialService.getAverageAge());
    }

    @GetMapping("/minimum-coordinates")
    public ResponseEntity<?> minimumCoordinates() {
        return DataResponse.success(specialService.getMinimumCoordinatesDragon());
    }

    @GetMapping("/maximum-head")
    public ResponseEntity<?> maximumHead() {
        return DataResponse.success(specialService.getMaximumHeadDragon());
    }

    @GetMapping("/deepest-cave")
    public ResponseEntity<?> deepestCave() {
        return DataResponse.success(specialService.getDeepestCaveDragon());
    }

    @PostMapping("/create-killers")
    public ResponseEntity<?> createKillers(@RequestBody @Valid List<Person> killers) {
        killers.forEach(personService::addPerson);
        return SimpleResponse.success("Команда из " + killers.size() + " человек успешно добавлена");
    }

}
