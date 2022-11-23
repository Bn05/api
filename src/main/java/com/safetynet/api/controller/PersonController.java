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
    PersonService personService;

    @PostMapping(value = "/person")
    public String createPerson(@RequestBody Person person) {
        logger.info("Request post/person with this body request : " + person.toString());
        String returnString = personService.createPerson(person);
        logger.info("Response to post/person : " + returnString);
        return returnString;
    }

    @PutMapping(value = "/person")
    public String updatePerson(@RequestBody Person person) {

        logger.info("Request put/person with this body request :" + person.toString());
        String returnString = personService.updatePerson(person);
        logger.info("Response to put/person : " + returnString);
        return returnString;
    }

    @DeleteMapping(value = "/person")
    public String deletePerson(@RequestParam(value = "firstName") String firstName, @RequestParam(value = "lastName") String lastName) {
        logger.info("Request to delete/person with this param firstName = " + firstName + " and lastName = " + lastName);
        String returnString = personService.deletePerson(firstName, lastName);
        logger.info("Response to delete/person : " + returnString);
        return returnString;
    }

    @GetMapping(value = "/childAlert")
    public List<Object> childAlertAndFamilyByAdress(@RequestParam(value = "address") String address) {

        logger.info("Request get /childAlert with param address = " + address);

        List<Object> returnList = personService.getChildAlertAndFamilyByAddress(address);

        logger.info("Response to get /childAlert : " + returnList.toString());
        return returnList;

    }

    @GetMapping(value = "/fire")
    public Map<String, Object> getPersonMedicalRecordAndStationNumber(@RequestParam(value = "address") String address) {

        logger.info("Request to get/fire with param address = " + address);

        Map<String, Object> returnMap = personService.getPersonMedicalRecordAndStationNumber(address);

        logger.info("Response to get/fire : " + returnMap);

        return returnMap;

    }

    @GetMapping(value = "/personInfo")
    public List<Map<String, String>> getPersonInfo(@RequestParam(value = "firstName") String firstName, @RequestParam(value = "lastName") String lastName) {

        logger.info("Request to get /personInfo with param firstName = " + firstName + " and lastName = " + lastName);
        List<Map<String, String>> returnList = personService.getPersonInfo(firstName, lastName);
        logger.info("Response to get/personInfo : " + returnList);
        return returnList;
    }


    @GetMapping(value = "/communityEmail")
    public List<String> getEmailByCity(@RequestParam(value = "city") String city) {
        logger.info("Request to get/communityEmail with param city = " + city);
        List<String> returnList = personService.getEmailByCity(city);
        logger.info("Response to get/communityEmail : " + returnList);
        return returnList;
    }


}
