package com.safetynet.api.controller;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.MedicalRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class MedicalRecordController {

    @Autowired
    MedicalRecordService medicalRecordService;

    Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    @PostMapping(value = "/medicalRecord")
    public String createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        logger.info("Request to post/medicalRecord with body param = "+medicalRecord);
        String returnString = medicalRecordService.createMedicalRecord(medicalRecord);
        logger.info("Response to post/medicalRecord : "+returnString);
        return returnString;
    }

    @PutMapping(value = "/medicalRecord")
    public String updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        logger.info("Request to put/medicalRecord with body param : "+medicalRecord);
        String returnString = medicalRecordService.updateMedicalRecord(medicalRecord);
        logger.info("Response to put/medicalRecord : "+returnString);
        return returnString;
    }

    @DeleteMapping(value = "/medicalRecord")
    public String deleteMedicalRecord(@RequestParam(value = "firstName") String firstName, @RequestParam(value = "lastName") String lastName) {
        logger.info("Request to delete/medicalRecord with param firstName = "+firstName+" and lastName = "+lastName);
        String returnString = medicalRecordService.deleteMedicalRecord(firstName, lastName);
        logger.info("Response to delete/medicalRecort : "+returnString);
        return returnString;
    }
}
