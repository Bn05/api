package com.safetynet.api.service;

import com.safetynet.api.model.Person;
import com.safetynet.api.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Iterable<Person> getPersons() {

        return personRepository.getPersonMap().values();

    }

}