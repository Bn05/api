package com.safetynet.api.controller;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/persons")
    public Iterable<Person> getPersons (){return personService.getPersons();}
}
