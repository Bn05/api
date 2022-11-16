package com.safetynet.api.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Data
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

            JsonNode birthdate = medicalRecord.path("birthdate");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            formatter = formatter.withLocale(Locale.FRANCE);
            LocalDate birthdateLocalDate = LocalDate.parse(birthdate.asText(), formatter);

            medicalRecordList.add(new MedicalRecord(
                    medicalRecord.path("firstName").asText(),
                    medicalRecord.path("lastName").asText(),
                    birthdateLocalDate,
                    medicationMap,
                    allergieList
            ));
        }

        for (MedicalRecord medicalRecord : medicalRecordList) {
            medicalRecordMap.put(medicalRecord.getFirstName() + medicalRecord.getLastName(), medicalRecord);
        }
    }

    public String createMedicalRecord(MedicalRecord medicalRecord) {

        medicalRecordMap.put(medicalRecord.getFirstName() + medicalRecord.getLastName(), medicalRecord);

        return "Medical Record Create !!";
    }

    public String updateMedicalRecord(MedicalRecord medicalRecord) {

        medicalRecordMap.put(medicalRecord.getFirstName() + medicalRecord.getLastName(), medicalRecord);

        return "Medical Record Update !!";
    }

    public String deleteMedicalRecord(String firstName, String lastName) {

        medicalRecordMap.remove(firstName + lastName);

        return "Medical Record Delete !!";
    }

    public MedicalRecord getMedicalRecord(String firstName, String lastName) {
        return medicalRecordMap.get(firstName + lastName);
    }

    public Map<String, Integer> countAdultAndChild(List<Person> personList) {

        Map<String, Integer> listCount = new HashMap<>();
        listCount.put("Adult", 0);
        listCount.put("Children", 0);

        for (Person person : personList) {
            Period age = getAge(person);
            if (age.getYears() > 18) {
                listCount.put("Adult", listCount.get("Adult") + 1);
            } else {
                listCount.put("Children", listCount.get("Children") + 1);
            }
        }
        return listCount;
    }

    public Period getAge(Person person) {

        LocalDate now = LocalDate.now();
        LocalDate birthdate = getMedicalRecord(person.getFirstName(), person.getLastName()).getBirthdate();
        return Period.between(birthdate, now);
    }

    public List<String> getAllergie(String firstName, String lastName) {

        return getMedicalRecord(firstName, lastName).getAllergies();
    }

    public Map<String, String> getMedication(String firstName, String lastName) {

        return getMedicalRecord(firstName, lastName).getMedications();
    }

}