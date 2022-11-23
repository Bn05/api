package com.safetynet.api.service;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.repository.MedicalRecordRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class MedicalRecordServiceTest {

    @InjectMocks
    MedicalRecordService medicalRecordService;

    @Mock
    MedicalRecordRepository medicalRecordRepository;

    private MedicalRecord medicalRecord = new MedicalRecord("firstName","lastName",null,null,null);


    @Test
    void createMedicalRecord() {
        medicalRecordService.createMedicalRecord(medicalRecord);

        verify(medicalRecordRepository, times(1)).createMedicalRecord(any());
    }

    @Test
    void updateMedicalRecord() {
        medicalRecordService.updateMedicalRecord(medicalRecord);

        verify(medicalRecordRepository, times(1)).updateMedicalRecord(any());
    }

    @Test
    void deleteMedicalRecord() {
        medicalRecordService.deleteMedicalRecord(medicalRecord.getFirstName(),medicalRecord.getLastName());

        verify(medicalRecordRepository, times(1)).deleteMedicalRecord(any(),any());
    }

   /* @Test
    void getMedicalRecord() {
        medicalRecordService.getMedicalRecord(medicalRecord.getFirstName(),medicalRecord.getLastName());

        verify(medicalRecordRepository, times(1)).getMedicalRecord(any(),any());
    }

    */
}