package com.safetynet.api.controller;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.model.PersonLite;
import com.safetynet.api.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

    @GetMapping(value = "/firestation")
    public List<PersonLite> getPersonsByStation(@RequestParam(value = "stationNumber") int stationNumber) {

        return firestationService.getPersonsByStation(stationNumber);
    }

    @PostMapping("/firestation")
    public String createFirestation(@RequestBody Firestation firestation) {
        return firestationService.createFirestation(firestation);

    }

    @DeleteMapping(value ="/firestation")
    public String deleteFirestation (@RequestParam(value = "firestation") String firestation){

        return firestationService.deleteFirestation(firestation);
    }

    @PutMapping(value = "/firestation")
    public String updateFiresation(@RequestBody Firestation firestation){
        return firestationService.updateFiresation(firestation);
    }


}
