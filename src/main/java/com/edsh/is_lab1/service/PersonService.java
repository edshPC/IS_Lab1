package com.edsh.is_lab1.service;

import com.edsh.is_lab1.exception.AppException;
import com.edsh.is_lab1.model.Person;
import com.edsh.is_lab1.repository.LocationRepository;
import com.edsh.is_lab1.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final LocationRepository locationRepository;
    private final PersonRepository personRepository;

    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    public Person getPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new AppException("No person with id " + id, HttpStatus.BAD_REQUEST));
    }

    public void checkUniquePassport(Person person) {
        if (person == null) return;
        var existing = personRepository.findFirstByPassportID(person.getPassportID());
        existing.ifPresent(p -> {
            if (!p.getId().equals(person.getId())) {
                throw new AppException("Duplicate passport with person " + p.getId(), HttpStatus.CONFLICT);
            }
        });
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addPerson(Person person) {
        applyExistingFields(person);
        personRepository.save(person);
        checkUniquePassport(person);
    }

    @Transactional
    public void removePerson(Person person) {
        person = getPersonById(person.getId());
        personRepository.delete(person);

        if (personRepository.countByLocation(person.getLocation()) == 0) {
            locationRepository.delete(person.getLocation());
        }
    }

    public void applyExistingFields(Person person) {
        var id = person.getLocation().getId();
        if (id != null) locationRepository.findById(id).ifPresent(person::setLocation);
    }

    public void applyExistingIds(Person person) {
        var existing = getPersonById(person.getId());
        if (person.getLocation() != null && person.getLocation().getId() == null)
            person.getLocation().setId(existing.getLocation().getId());
    }

}
