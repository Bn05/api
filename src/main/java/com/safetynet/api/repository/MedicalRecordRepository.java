package com.safetynet.api.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.Error.ErrorAlreadyExistException;
import com.safetynet.api.Error.ErrorNoExistException;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Repository
public class MedicalRecordRepository {

    Logger logger = LoggerFactory.getLogger(MedicalRecordRepository.class);

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
        logger.debug("Call MedicalRecordRepository.createMedicalRecord with param = " + medicalRecord.toString());
        try {
            if (!medicalRecordMap.containsKey(medicalRecord.getFirstName() + medicalRecord.getLastName())) {
                medicalRecordMap.put(medicalRecord.getFirstName() + medicalRecord.getLastName(), medicalRecord);
                return "Medical Record Create !!";
            } else {
                throw new ErrorAlreadyExistException("Medical Record Already Exist !!");
            }
        } catch (ErrorAlreadyExistException e) {
            logger.error("Medical Record Already Exist !!");
            return "Medical Record Already Exist !!";
        }
    }

    public String updateMedicalRecord(MedicalRecord medicalRecord) {
        logger.debug("Call MedicalRecordRepository.updateMedicalRecord with param = " + medicalRecord.toString());
        try {
            if (medicalRecordMap.containsKey(medicalRecord.getFirstName() + medicalRecord.getLastName())) {
                medicalRecordMap.put(medicalRecord.getFirstName() + medicalRecord.getLastName(), medicalRecord);
                return "Medical Record Update !!";
            } else {
                throw new ErrorNoExistException("Medical Record doesn't exist !");
            }
        } catch (ErrorNoExistException e) {
            logger.error("Medical Record doesn't exist !");
            return "Medical Record doesn't exist !";
        }
    }

    public String deleteMedicalRecord(String firstName, String lastName) {
        logger.debug("Call MedicalRecordRepository.deleteMedicalRecord with param = " + firstName + "," + lastName);
        try {
            if (medicalRecordMap.containsKey(firstName + lastName)) {
                medicalRecordMap.remove(firstName + lastName);
                return "Medical Record Delete !!";
            } else {
                throw new ErrorNoExistException("Medical Record doesn't exist !");
            }
        } catch (ErrorNoExistException e) {
            logger.error("Medical Record doesn't exist !");
            return "Medical Record doesn't exist !";
        }
    }

    public MedicalRecord getMedicalRecord(String firstName, String lastName) {
        logger.debug("Call MedicalRecordRepository.getMedicalRecord with param = " + firstName + "," + lastName);
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

        logger.debug("Call MedicalRecordRepository.getAge with param = "+person);
        LocalDate now = LocalDate.now();
        LocalDate birthdate = getMedicalRecord(person.getFirstName(), person.getLastName()).getBirthdate();
        return Period.between(birthdate, now);
    }

    public List<String> getAllergie(String firstName, String lastName) {
        logger.debug("Call MedicalRecordRepository.getAllergie with param = " + firstName + "," + lastName);
        return getMedicalRecord(firstName, lastName).getAllergies();
    }

    public Map<String, String> getMedication(String firstName, String lastName) {
        logger.debug("Call MedicalRecordRepository.getMedication with param = " + firstName + "," + lastName);

        return getMedicalRecord(firstName, lastName).getMedications();
    }

}