package com.edsh.is_lab1.repository;

import com.edsh.is_lab1.model.Location;
import com.edsh.is_lab1.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    long countByLocation(Location location);
}
