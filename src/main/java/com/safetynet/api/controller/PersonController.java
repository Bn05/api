package com.safetynet.api.controller;

import com.safetynet.api.model.Person;
import com.safetynet.api.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PersonController {

    Logger logger = LoggerFactory.getLogger(PersonController.class);

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
        return personService.getChildAlertAndFamilyByAddress(address);
    }

    @GetMapping(value = "/fire")
    public Map<String,Object> getPersonMedicalRecordAndStationNumber(@RequestParam(value="address")String address){
        return personService.getPersonMedicalRecordAndStationNumber(address);
    }

    @GetMapping(value = "/personInfo")
    public List<Map<String, String>> getPersonInfo (@RequestParam(value = "firstName")String firstName,@RequestParam(value = "lastName")String lastName){
        return personService.getPersonInfo(firstName,lastName);
    }


   @GetMapping(value = "/communityEmail")
    public List<String> getEmailByCity(@RequestParam(value = "city")String city){
        return personService.getEmailByCity(city);
    }


}
