package com.safetynet.api.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.model.MedicalRecord;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MedicalRecordRepository {

    private Map<String, MedicalRecord> medicalRecordMap = new HashMap<>();

    public void readJsonFile() throws IOException {

        List<MedicalRecord> medicalRecordList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File("src/main/resources/data.json")).get("medicalrecords");

        for (JsonNode medicalRecord : jsonNode) {

            JsonNode medications = medicalRecord.path("medications");
            Map<String, String> medicationMap = new HashMap<>();
            for (JsonNode medication : medications) {
                String[] word = medication.asText().split(":");
                medicationMap.put(word[0], word[1]);
            }

            JsonNode allergies = medicalRecord.path("allergies");
            List<String> allergieList = new ArrayList<>();
            for (JsonNode allergie : allergies) {
                allergieList.add(allergie.asText());
            }


            medicalRecordList.add(new MedicalRecord(
                    medicalRecord.path("firstName").asText(),
                    medicalRecord.path("lastName").asText(),
                    medicalRecord.path("birthdate").asText(),
                    medicationMap,
                    allergieList
            ));
        }

        for (MedicalRecord medicalRecord : medicalRecordList) {
            medicalRecordMap.put(medicalRecord.getFirstName() + medicalRecord.getLastName(), medicalRecord);
        }
    }

    public Map<String, MedicalRecord> getMedicalRecordMap() {
        return medicalRecordMap;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordMap.put(medicalRecord.getFirstName() + medicalRecord.getLastName(), medicalRecord);

    }
}