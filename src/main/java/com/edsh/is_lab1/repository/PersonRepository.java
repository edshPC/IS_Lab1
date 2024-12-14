package com.edsh.is_lab1.repository;

import com.edsh.is_lab1.model.Location;
import com.edsh.is_lab1.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    long countByLocation(Location location);
    Optional<Person> findFirstByPassportID(String passportID);
}
