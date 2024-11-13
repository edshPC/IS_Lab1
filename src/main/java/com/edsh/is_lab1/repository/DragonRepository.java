package com.edsh.is_lab1.repository;

import com.edsh.is_lab1.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DragonRepository extends JpaRepository<Dragon, Integer> {
    long countByCoordinates(Coordinates coordinates);
    long countByCave(DragonCave cave);
    long countByHead(DragonHead head);
    long countByKiller(Person killer);
}
