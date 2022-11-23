package com.safetynet.api.service;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.repository.MedicalRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MedicalRecordService {

    Logger logger = LoggerFactory.getLogger(MedicalRecordService.class);

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    public String createMedicalRecord(MedicalRecord medicalRecord) {
        logger.debug("Call MedicalRecordService.createMedicalRecord with param = " + medicalRecord.toString());
        String returnString = medicalRecordRepository.createMedicalRecord(medicalRecord);
        logger.debug("Response to medicalRecordRepository.createMedicalRecord : " + returnString);
        return returnString;
    }

    public String updateMedicalRecord(MedicalRecord medicalRecord) {
        logger.debug("Call MedicalRecordService.updateMedicalRecord with param = " + medicalRecord.toString());
        String returnString = medicalRecordRepository.updateMedicalRecord(medicalRecord);
        logger.debug("Response to medicalRecordRepository.updateMedicalRecord : " + returnString);
        return returnString;
    }

    public String deleteMedicalRecord(String firstName, String lastName) {
        logger.debug("Call MedicalRecordService.deleteMedicalRecord with param : " + firstName + "," + lastName);
        String returnString = medicalRecordRepository.deleteMedicalRecord(firstName, lastName);
        logger.debug("Response to medicalRecordRepository.deleteMedicalRecord : " + returnString);
        return returnString;
    }

}
