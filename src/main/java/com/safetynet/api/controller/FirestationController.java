package com.safetynet.api.controller;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.service.FirestationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class FirestationController {

    Logger logger = LoggerFactory.getLogger(FirestationController.class);
    @Autowired
    private FirestationService firestationService;

    @GetMapping(value = "/firestation")
    public List<Object> getPersonsByStationNumber(@RequestParam(value = "stationNumber") int stationNumber) {
        logger.info("Request get/firestation with param stationNumber = " + stationNumber);
        List<Object> returnList = firestationService.getPersonsByStationNumber(stationNumber);
        logger.info("Response to get/firestation : " + returnList);
        return returnList;
    }

    @GetMapping(value = "/phoneAlert")
    public List<String> getPhoneNumberByStationNumber(@RequestParam(value = "firestation") int stationNumber) {
        logger.info("Request get/phoneAlert with param firestation = " + stationNumber);
        List<String> returnList = firestationService.getPhoneNumberByStationNumber(stationNumber);
        logger.info("Response to get/phoneAlert : " + returnList);
        return returnList;
    }

    @GetMapping(value = "flood/stations")
    public Map<String, List<String>> getFamilyByFireStation(@RequestParam(value = "stations") List<Integer> stations) {
        logger.info("Request get/flood/stations with param stations = " + stations);
        Map<String, List<String>> returnMap = firestationService.getFamilyByFireStation(stations);
        logger.info("Response to get/flood/stations : " + returnMap);
        return returnMap;
    }

    @PostMapping("/firestation")
    public String createFirestation(@RequestBody Firestation firestation) {
        logger.info("Request post/firestation with body request = " + firestation);
        String returnString = firestationService.createFirestation(firestation);
        logger.info("Response to post/firestation : " + returnString);
        return returnString;

    }

    @PutMapping(value = "/firestation")
    public String updateFiresation(@RequestBody Firestation firestation) {
        logger.info("Request to put/firestation with body param = " + firestation);
        String returnString = firestationService.updateFiresation(firestation);
        logger.info("Response to put/firestation : " + returnString);
        return returnString;

    }

    @DeleteMapping(value = "/firestation")
    public String deleteFirestation(@RequestBody Firestation firestation) {
        logger.info("Request to delete/firestation with body param = " + firestation);
        String returnString = firestationService.deleteFirestation(firestation);
        logger.info("Response to delete/firestation : " + returnString);
        return returnString;
    }
}
