package com.edsh.is_lab1.service;

import com.edsh.is_lab1.entity.User;
import com.edsh.is_lab1.exception.AppException;
import com.edsh.is_lab1.model.Coordinates;
import com.edsh.is_lab1.model.Dragon;
import com.edsh.is_lab1.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DragonService {
    private final DragonRepository dragonRepository;
    private final CoordinatesRepository coordinatesRepository;
    private final DragonCaveRepository dragonCaveRepository;
    private final DragonHeadRepository dragonHeadRepository;
    private final PersonRepository personRepository;
    private final LocationRepository locationRepository;

    public List<Dragon> getAllDragons() {
        return dragonRepository.findAll();
    }

    public Dragon getDragonById(Integer id) {
        return dragonRepository.findById(id)
                .orElseThrow(() -> new AppException("No dragon with id " + id, HttpStatus.NOT_FOUND));
    }

    public void addDragon(Dragon dragon, User owner) {
        dragon.setOwner(owner);
        applyExistingFields(dragon);
        dragonRepository.save(dragon);
    }

    public void updateDragon(Dragon dragon) {
        applyExistingFields(dragon);
        var existing = getDragonById(dragon.getId());
        existing.applyDataFrom(dragon);
        dragonRepository.save(existing);
    }

    public void removeDragon(Dragon dragon) {
        if (!dragonRepository.existsById(dragon.getId()))
            throw new AppException("No dragon with id " + dragon.getId(), HttpStatus.NOT_FOUND);
        dragonRepository.deleteById(dragon.getId());
    }

    public void checkDragonOwner(Dragon dragon, User owner) {
        if (dragon.getOwner() != null &&
            !dragon.getOwner().getLogin().equals(owner.getLogin()) &&
            owner.getPermission() != User.Permission.ADMIN)
            throw new AppException("You don't have access to dragon " + dragon.getId(), HttpStatus.FORBIDDEN);
    }

    public void applyExistingFields(Dragon dragon) {
        var id = dragon.getCoordinates().getId();
        if (id != null) dragon.setCoordinates(coordinatesRepository.findById(id).orElseThrow());
        id = dragon.getCave().getId();
        if (id != null) dragon.setCave(dragonCaveRepository.findById(id).orElseThrow());
        id = dragon.getHead().getId();
        if (id != null) dragon.setHead(dragonHeadRepository.findById(id).orElseThrow());
        id = dragon.getKiller().getId();
        if (id != null) dragon.setKiller(personRepository.findById(id).orElseThrow());
        var killer = dragon.getKiller();
        id = killer.getLocation().getId();
        if (id != null) killer.setLocation(locationRepository.findById(id).orElseThrow());
    }

}
