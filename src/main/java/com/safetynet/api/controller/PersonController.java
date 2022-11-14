package com.safetynet.api.controller;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping(value = "/person")
    public String createPerson(@RequestBody Person person) {
        return personService.createPerson(person);
    }

    @PutMapping(value = "/person")
    public String updatePerson(@RequestBody Person person) {
        return personService.updatePerson(person);
    }

    @DeleteMapping(value = "/person")
    public String deletePerson(@RequestParam(value = "firstName") String firstName, @RequestParam(value = "lastName") String lastName) {
        return personService.deletePerson(firstName, lastName);
    }

    @GetMapping(value = "/childAlert")
    public List<Object> childAlertAndFamilyByAdress(@RequestParam(value = "address") String address) {
        return personService.childAlertAndFamilyByAdress(address);
    }


}
