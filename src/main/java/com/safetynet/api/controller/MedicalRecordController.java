package com.safetynet.api.controller;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MedicalRecordController {

    @Autowired
    MedicalRecordService medicalRecordService;

    @PostMapping(value = "/medicalRecord")
    public String createMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.createMedicalRecord(medicalRecord);
    }

    @PutMapping(value = "/medicalRecord")
    public String updateMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
        return medicalRecordService.updateMedicalRecord(medicalRecord);
    }

    @DeleteMapping(value = "/medicalRecord")
    public String deleteMedicalRecord(@RequestParam(value = "firstName") String firstName, @RequestParam(value = "lastName") String lastName) {
        return medicalRecordService.deleteMedicalRecord(firstName, lastName);
    }

    @GetMapping(value = "/medicalRecordby")
    public MedicalRecord getMedicalRecord (@RequestParam(value = "firstName")String firstName,@RequestParam(value = "lastName")String lastName){
        return medicalRecordService.getMedicalRecord(firstName, lastName);
        //TODO : a supprimer
    }

}
