package com.safetynet.api.service;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MedicalRecordService {
    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    public String createMedicalRecord(MedicalRecord medicalRecord) {
        return medicalRecordRepository.createMedicalRecord(medicalRecord);
    }

    public String updateMedicalRecord(MedicalRecord medicalRecord) {
         return medicalRecordRepository.updateMedicalRecord(medicalRecord);
    }

    public String deleteMedicalRecord(String firstName, String lastName) {
        return medicalRecordRepository.deleteMedicalRecord(firstName, lastName);
    }

}
