package com.safetynet.api.service;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.FirestationRepository;

import com.safetynet.api.repository.MedicalRecordRepository;
import com.safetynet.api.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class FirestationService {
    @Autowired
    private FirestationRepository firestationRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    public List<Object> getPersonsByStationNumber(int stationNumber) {

        List<Firestation> firestationSelectList = firestationRepository.getFirestationsByNumber(stationNumber);

        List<Person> personSelectList = new ArrayList<>();
        for (Firestation firestation : firestationSelectList) {
            personSelectList.addAll(personRepository.getPersonByStation(firestation));
        }

        List<Object> personLiteSelectList = new ArrayList<>();
        for (Person person : personSelectList) {
            Map<String, String> personMap = new LinkedHashMap<>();
            personMap.put("firstName", person.getFirstName());
            personMap.put("lastName", person.getLastName());
            personMap.put("adress", person.getAddress());
            personMap.put("phone", person.getPhone());
            personLiteSelectList.add(personMap);
        }

        Map<String, Integer> countAdultAndChildMap = medicalRecordRepository.countAdultAndChild(personSelectList);

        return Arrays.asList(countAdultAndChildMap, personLiteSelectList);
    }

    public List<String> getPhoneNumberByStationNumber(int stationNumber) {

        List<Firestation> firestationList = firestationRepository.getFirestationsByNumber(stationNumber);
        List<String> phoneNumberList = new ArrayList<>();

        for (Firestation firestation : firestationList) {

            List<Person> personList = personRepository.getPersonByStation(firestation);
            for (Person person : personList) {
                phoneNumberList.add(person.getPhone());
            }
        }
        return phoneNumberList;
    }

    public Map<String, List<String>> getFamilyByFireStation(List<Integer> firestationNumberList) {

        List<Firestation> firestationList = new ArrayList<>();

        for (int firestationNumber : firestationNumberList) {
            firestationList.addAll(firestationRepository.getFirestationsByNumber(firestationNumber));
        }

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

            result.put("Address : "+firestation.getAddress(), personStringList);

        }

        return result;


    }


    public String createFirestation(Firestation firestation) {
        return firestationRepository.createFirestation(firestation);
    }

    public String deleteFirestation(String firestation) {

        return firestationRepository.deleteFirestation(firestation);
    }

    public String updateFiresation(Firestation firestation) {
        return firestationRepository.updateFiresation(firestation);
    }

}


