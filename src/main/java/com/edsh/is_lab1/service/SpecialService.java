package com.edsh.is_lab1.service;

import com.edsh.is_lab1.model.Dragon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecialService {

    private final DragonService dragonService;

    public Double getAverageAge() {
        var dragons = dragonService.getAllDragons();
        if (dragons.isEmpty()) return null;
        return dragons.stream()
                .map(Dragon::getAge)
                .mapToDouble(Long::doubleValue)
                .average()
                .orElseThrow();
    }

    public Dragon getMinimumCoordinatesDragon() {
        var dragons = dragonService.getAllDragons();
        return dragons.stream()
                .reduce((a, b) -> a.getCoordinates().getX() + a.getCoordinates().getY()
                < b.getCoordinates().getX() + b.getCoordinates().getY() ? a : b)
                .orElse(null);
    }

    public Dragon getMaximumHeadDragon() {
        var dragons = dragonService.getAllDragons();
        return dragons.stream()
                .reduce((a, b) ->
                        a.getHead().getSize() > b.getHead().getSize() ? a : b)
                .orElse(null);
    }

    public Dragon getDeepestCaveDragon() {
        var dragons = dragonService.getAllDragons();
        return dragons.stream()
                .reduce((a, b) ->
                        a.getCave().getDepth() > b.getCave().getDepth() ? a : b)
                .orElse(null);
    }

}
