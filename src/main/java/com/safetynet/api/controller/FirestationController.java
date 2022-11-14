package com.safetynet.api.controller;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.model.Person;
import com.safetynet.api.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

    @GetMapping(value = "/firestation")
    public Iterable<Person> getPersonsByStation(@RequestParam(value = "stationNumber") int stationNumber) {

        return firestationService.getPersonsOfFirestation(stationNumber);
    }

    @PostMapping("/firestation")
    public String createFirestation(@RequestBody Firestation firestation) {
        return firestationService.createFirestation(firestation);

    }
}
