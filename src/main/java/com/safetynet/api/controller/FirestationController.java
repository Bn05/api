package com.safetynet.api.controller;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.service.FirestationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class FirestationController {

    @Autowired
    private FirestationService firestationService;

    @GetMapping(value = "/firestation")
    public List<Object> getPersonsByStationNumber(@RequestParam(value = "stationNumber") int stationNumber) {

        return firestationService.getPersonsByStationNumber(stationNumber);
    }

    @GetMapping(value = "/phoneAlert")
    public List<String> getPhoneNumberByStationNumber(@RequestParam(value = "firestation") int stationNumber) {
        return firestationService.getPhoneNumberByStationNumber(stationNumber);
    }

    @GetMapping(value = "flood/stations")
    public Map<String, List<String>> getFamilyByFireStation(@RequestParam(value = "stations") List<Integer> stations) {
        return firestationService.getFamilyByFireStation(stations);
    }

    @PostMapping("/firestation")
    public String createFirestation(@RequestBody Firestation firestation) {
        return firestationService.createFirestation(firestation);
    }

    @PutMapping(value = "/firestation")
    public String updateFiresation(@RequestBody Firestation firestation) {
        return firestationService.updateFiresation(firestation);
    }

    @DeleteMapping(value = "/firestation")
    public String deleteFirestation(@RequestBody Firestation firestation) {

        return firestationService.deleteFirestation(firestation);
    }
}
