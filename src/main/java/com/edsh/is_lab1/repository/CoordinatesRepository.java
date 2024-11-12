package com.edsh.is_lab1.repository;

import com.edsh.is_lab1.model.Coordinates;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {
}
