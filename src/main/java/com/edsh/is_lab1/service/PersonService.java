package com.edsh.is_lab1.service;

import com.edsh.is_lab1.exception.AppException;
import com.edsh.is_lab1.model.Person;
import com.edsh.is_lab1.repository.LocationRepository;
import com.edsh.is_lab1.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final LocationRepository locationRepository;
    private final PersonRepository personRepository;

    public Person getPersonById(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new AppException("No person with id " + id, HttpStatus.BAD_REQUEST));
    }

    public void removePerson(Person person) {
        person = getPersonById(person.getId());
        personRepository.delete(person);

        if (personRepository.countByLocation(person.getLocation()) == 0) {
            locationRepository.delete(person.getLocation());
        }
    }

    public void applyExistingFields(Person person) {
        var id = person.getLocation().getId();
        if (id != null) person.setLocation(locationRepository.findById(id).orElseThrow());
    }

}
