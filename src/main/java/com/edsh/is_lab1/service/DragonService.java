package com.edsh.is_lab1.service;

import com.edsh.is_lab1.entity.User;
import com.edsh.is_lab1.exception.AppException;
import com.edsh.is_lab1.model.Dragon;
import com.edsh.is_lab1.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DragonService {
    private final DragonRepository dragonRepository;
    private final CoordinatesRepository coordinatesRepository;
    private final DragonCaveRepository dragonCaveRepository;
    private final DragonHeadRepository dragonHeadRepository;
    private final PersonRepository personRepository;
    private final PersonService personService;

    public List<Dragon> getAllDragons() {
        return dragonRepository.findAll();
    }

    public void checkExistsDragon(Dragon dragon) {
        if (!dragonRepository.existsById(dragon.getId()))
            throw new AppException("No dragon with id " + dragon.getId());
    }

    public Dragon getDragonById(Integer id) {
        return dragonRepository.findById(id)
                .orElseThrow(() -> new AppException("No dragon with id " + id, HttpStatus.NOT_FOUND));
    }

    @Transactional
    public void addDragon(Dragon dragon, User owner) {
        dragon.setOwner(owner);
        applyExistingFields(dragon);
        dragonRepository.save(dragon);
    }

    @Transactional
    public void addDragons(List<Dragon> dragons, User owner) {
        dragons.forEach(dragon -> addDragon(dragon, owner));
    }

    @Transactional
    public void updateDragon(Dragon dragon) {
        applyExistingFields(dragon);
        applyExistingIds(dragon);
        dragonRepository.save(dragon);
    }

    @Transactional
    public void removeDragon(Dragon dragon) {
        dragon = getDragonById(dragon.getId());
        dragonRepository.delete(dragon);
        clearUnusedEntities(dragon);
    }

    public void clearUnusedEntities(Dragon dragon) {
        if (dragonRepository.countByCoordinates(dragon.getCoordinates()) == 0) {
            coordinatesRepository.delete(dragon.getCoordinates());
        }
        if (dragonRepository.countByCave(dragon.getCave()) == 0) {
            dragonCaveRepository.delete(dragon.getCave());
        }
        if (dragonRepository.countByHead(dragon.getHead()) == 0) {
            dragonHeadRepository.delete(dragon.getHead());
        }
        if (dragonRepository.countByKiller(dragon.getKiller()) == 0) {
            personService.removePerson(dragon.getKiller());
        }
    }

    public void checkDragonOwner(Dragon dragon, User user) {
        if (dragon.getOwner() != null &&
            !dragon.getOwner().getUsername().equals(user.getUsername()) &&
            user.getPermission() != User.Permission.ADMIN)
            throw new AppException("You don't have access to dragon " + dragon.getId(), HttpStatus.FORBIDDEN);
    }

    public void applyExistingFields(Dragon dragon) {
        Long id;
        if (dragon.getCoordinates() != null) {
            id = dragon.getCoordinates().getId();
            if (id != null) dragon.setCoordinates(coordinatesRepository.findById(id).orElseThrow());
            else coordinatesRepository
                    .findByXAndY(dragon.getCoordinates().getX(), dragon.getCoordinates().getY())
                    .ifPresent(dragon::setCoordinates);
        }
        if (dragon.getCave() != null) {
            id = dragon.getCave().getId();
            if (id != null) dragon.setCave(dragonCaveRepository.findById(id).orElseThrow());
        }
        if (dragon.getHead() != null) {
            id = dragon.getHead().getId();
            if (id != null) dragon.setHead(dragonHeadRepository.findById(id).orElseThrow());
        }
        if (dragon.getKiller() != null) {
            id = dragon.getKiller().getId();
            if (id != null) dragon.setKiller(personRepository.findById(id).orElseThrow());
            personService.applyExistingFields(dragon.getKiller());
        }
    }

    public void applyExistingIds(Dragon dragon) {
        var existing = getDragonById(dragon.getId());
        if (dragon.getCoordinates() != null && dragon.getCoordinates().getId() == null)
            dragon.getCoordinates().setId(existing.getCoordinates().getId());
        if (dragon.getCave() != null && dragon.getCave().getId() == null)
            dragon.getCave().setId(existing.getCave().getId());
        if (dragon.getHead() != null && dragon.getHead().getId() == null)
            dragon.getHead().setId(existing.getHead().getId());
        if (dragon.getKiller() != null && dragon.getKiller().getId() == null) {
            dragon.getKiller().setId(existing.getKiller().getId());
            personService.applyExistingIds(dragon.getKiller());
        }
    }

}
