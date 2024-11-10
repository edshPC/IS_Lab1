package com.edsh.is_lab1.service;

import com.edsh.is_lab1.exception.AppException;
import com.edsh.is_lab1.model.Dragon;
import com.edsh.is_lab1.repository.DragonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DragonService {
    private final DragonRepository dragonRepository;

    public List<Dragon> getAllDragons() {
        return dragonRepository.findAll();
    }

    public Dragon getDragonById(Integer id) {
        return dragonRepository.findById(id)
                .orElseThrow(() -> new AppException("No dragon with id " + id, HttpStatus.NOT_FOUND));
    }

    public void addDragon(Dragon dragon) {
        dragonRepository.save(dragon);
    }

    public void updateDragon(Dragon dragon) {
        var existing = getDragonById(dragon.getId());
        existing.applyDataFrom(dragon);
        dragonRepository.save(existing);
    }

    public void removeDragon(Dragon dragon) {
        if (!dragonRepository.existsById(dragon.getId()))
            throw new AppException("No dragon with id " + dragon.getId(), HttpStatus.NOT_FOUND);
        dragonRepository.deleteById(dragon.getId());
    }

}
