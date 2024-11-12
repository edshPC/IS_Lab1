package com.edsh.is_lab1.repository;

import com.edsh.is_lab1.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Long> {
}
