package com.safetynet.api.service;

import com.safetynet.api.model.Person;
import com.safetynet.api.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public String createPerson(Person person) {
        return personRepository.createPerson(person);
    }

    public String updatePerson(Person person) {
        return personRepository.updatePerson(person);
    }

    public String deletePerson(String firstName, String lastName) {
        return personRepository.deletePerson(firstName, lastName);
    }
}