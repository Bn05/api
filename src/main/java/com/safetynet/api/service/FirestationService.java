package com.safetynet.api.service;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.FirestationRepository;

import com.safetynet.api.repository.MedicalRecordRepository;
import com.safetynet.api.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class FirestationService {
    Logger logger = LoggerFactory.getLogger(FirestationService.class);

    @Autowired
    private FirestationRepository firestationRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public List<Object> getPersonsByStationNumber(int stationNumber) {
        logger.debug("Call FirestationService.getPersonsByStationNumber with param : " + stationNumber);

        List<Firestation> firestationSelectList = firestationRepository.getFirestationsByNumber(stationNumber);
        logger.debug("firestationSelectList = " + firestationSelectList);
        List<Person> personSelectList = new ArrayList<>();
        for (Firestation firestation : firestationSelectList) {
            personSelectList.addAll(personRepository.getPersonByStation(firestation));
        }
        logger.debug("personSelectList : " + personSelectList);
        List<Object> personLiteSelectList = new ArrayList<>();
        for (Person person : personSelectList) {
            Map<String, String> personMap = new LinkedHashMap<>();
            personMap.put("firstName", person.getFirstName());
            personMap.put("lastName", person.getLastName());
            personMap.put("address", person.getAddress());
            personMap.put("phone", person.getPhone());
            personLiteSelectList.add(personMap);
        }
        logger.debug("personLiteSelectList : " + personLiteSelectList);

        Map<String, Integer> countAdultAndChildMap = medicalRecordRepository.countAdultAndChild(personSelectList);
        logger.debug("Response to medicalRecordRepository.countAdultAndChild : " + countAdultAndChildMap.toString());

        return Arrays.asList(countAdultAndChildMap, personLiteSelectList);
    }

    public List<String> getPhoneNumberByStationNumber(int stationNumber) {
        logger.debug("Call FirestationService.getPhoneNumberByStationNumber with param = " + stationNumber);
        List<Firestation> firestationList = firestationRepository.getFirestationsByNumber(stationNumber);
        logger.debug("Response to firestationRepository.getFirestationsByNumber : " + firestationList);
        List<String> phoneNumberList = new ArrayList<>();

        for (Firestation firestation : firestationList) {

            List<Person> personList = personRepository.getPersonByStation(firestation);
            logger.debug("Response to personRepository.getPersonByStation : " + personList);
            for (Person person : personList) {
                phoneNumberList.add(person.getPhone());
            }
        }
        return phoneNumberList;
    }

    public Map<String, List<String>> getFamilyByFireStation(List<Integer> firestationNumberList) {
        logger.debug("Call FirestationService.getFamilyByFireStation with param = " + firestationNumberList.toString());

        List<Firestation> firestationList = new ArrayList<>();
        for (int firestationNumber : firestationNumberList) {
            firestationList.addAll(firestationRepository.getFirestationsByNumber(firestationNumber));
        }
        logger.debug(" firestationList = " + firestationList);

        Map<String, List<String>> result = new HashMap<>();
        for (Firestation firestation : firestationList) {

            List<Person> personList = personRepository.getPersonByStation(firestation);
            List<String> personStringList = new ArrayList<>();
            for (Person person : personList) {
                String firstName = person.getFirstName();
                String lastName = person.getLastName();
                String phone = person.getPhone();
                String age = String.valueOf(medicalRecordRepository.getAge(person).getYears());
                String medication = medicalRecordRepository.getMedicalRecord(firstName, lastName).getMedications().toString();
                String allergies = medicalRecordRepository.getMedicalRecord(firstName, lastName).getAllergies().toString();

                personStringList.add(firstName + " " + lastName + " || Phone : " + phone + " || Age : " + age + " || Medications : " + medication + " || Allergies : " + allergies);
            }
            logger.debug("personStringList = " + personStringList);
            result.put("Address : " + firestation.getAddress(), personStringList);
        }
        return result;
    }


    public String createFirestation(Firestation firestation) {
        logger.debug("Call FirestationService.createFirestation with param = " + firestation.toString());
        String returnString = firestationRepository.createFirestation(firestation);
        logger.debug("Response to firestationRepository.createFirestation :" + returnString);
        return returnString;
    }

    public String deleteFirestation(Firestation firestation) {
        logger.debug("Call FiresationService.deleteFirestation with param = " + firestation.toString());
        String returnString = firestationRepository.deleteFirestation(firestation);
        logger.debug("Response to firestationRepository.deleteFirestation : " + returnString);
        return returnString;
    }

    public String updateFiresation(Firestation firestation) {
        logger.debug("Call firestationService.updateFiresation with param = " + firestation.toString());
        String returnString = firestationRepository.updateFiresation(firestation);
        logger.debug("Response to firestationRepository.updateFiresation : " + returnString);
        return returnString;
    }

}


