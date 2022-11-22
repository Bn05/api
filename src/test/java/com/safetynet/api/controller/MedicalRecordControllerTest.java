package com.safetynet.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.service.MedicalRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class MedicalRecordControllerTest {

    @Autowired
    public MockMvc mockMvc;

    @MockBean
    MedicalRecordService medicalRecordService;


    @Test
    void createMedicalRecord() throws Exception {

        LocalDate birthdate0 =  LocalDate.of(1992,3,10);
        Map<String,String> medicationMap = new HashMap<>();
        List<String> allergieList = new ArrayList<>();
        MedicalRecord medicalRecord = new MedicalRecord("firstName","lastName", birthdate0,medicationMap,allergieList);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String requestJson = objectMapper.writeValueAsString(medicalRecord);

        when(medicalRecordService.createMedicalRecord(medicalRecord)).thenReturn("OK");

        mockMvc.perform(post("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        verify(medicalRecordService,times(1)).createMedicalRecord(any());
    }

    @Test
    void updateMedicalRecord() throws Exception {
        LocalDate birthdate0 =  LocalDate.of(1992,3,10);
        Map<String,String> medicationMap = new HashMap<>();
        List<String> allergieList = new ArrayList<>();
        MedicalRecord medicalRecord = new MedicalRecord("firstName","lastName", birthdate0,medicationMap,allergieList);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String requestJson = objectMapper.writeValueAsString(medicalRecord);

        when(medicalRecordService.updateMedicalRecord(medicalRecord)).thenReturn("OK");

        mockMvc.perform(put("/medicalRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        verify(medicalRecordService,times(1)).updateMedicalRecord(any());
            }

    @Test
    void deleteMedicalRecord() throws Exception {

        when(medicalRecordService.deleteMedicalRecord(any(),any())).thenReturn("OK");

        mockMvc.perform(delete("/medicalRecord")
                .param("firstName","firstName")
                .param("lastName","lastName"));

        verify(medicalRecordService,times(1)).deleteMedicalRecord(any(),any());

    }

}