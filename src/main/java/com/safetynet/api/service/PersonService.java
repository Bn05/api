package com.safetynet.api.service;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.FirestationRepository;
import com.safetynet.api.repository.MedicalRecordRepository;
import com.safetynet.api.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.*;

@Service
public class PersonService {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private FirestationRepository firestationRepository;

    public List<Object> getChildAlertAndFamilyByAdress(String adress) {

        List<Person> personList = personRepository.getPersonByAdress(adress);
        List<Person> childList = getChild(personList);

        List<Object> childInfoList = new ArrayList<>();
        for (Person child : childList) {
            List<Person> family = getFamily(child);

            List<Object> familyLite = new ArrayList<>();
            for (Person familymember : family) {
                Map<String, String> member = new HashMap<>();
                member.put("firstName", familymember.getFirstName());
                member.put("lastName", familymember.getLastName());
                familyLite.add(member);
            }

            Map<String, Object> childMap = new LinkedHashMap<>();
            childMap.put("FirstName", child.getFirstName());
            childMap.put("LastName", child.getLastName());
            childMap.put("Age", medicalRecordRepository.getAge(child).getYears());
            childMap.put("Family", familyLite);

            childInfoList.add(childMap);
        }

        return childInfoList;


    }

    public Map<String, Object> getPersonMedicalRecordAndStationNumber(String address) {

        List<Person> personList = personRepository.getPersonByAdress(address);
        Firestation firestation = firestationRepository.getFirestationByAddress(address);

        List<Object> personLiteList = new ArrayList<>();
        for (Person person : personList) {

            Map<String, Object> personMap = new LinkedHashMap<>();

            List<String> allergieList = medicalRecordRepository.getMedicalRecord(person.getFirstName(), person.getLastName()).getAllergies();
            Map<String, String> medicationMap = medicalRecordRepository.getMedicalRecord(person.getFirstName(), person.getLastName()).getMedications();

            personMap.put("firstName", person.getFirstName());
            personMap.put("lastName", person.getLastName());
            personMap.put("phone", person.getPhone());
            personMap.put("age", medicalRecordRepository.getAge(person).getYears());
            personMap.put("allergies", allergieList);
            personMap.put("medications", medicationMap);

            personLiteList.add(personMap);
        }

        Map<String, Object> objectMap = new LinkedHashMap<>();

        objectMap.put("firesation", firestation.getStation());
        objectMap.put("person", personLiteList);

        return objectMap;
    }


    public String createPerson(Person person) {
        return personRepository.createPerson(person);
    }

    public String updatePerson(Person person) {
        return personRepository.updatePerson(person);
    }

    public String deletePerson(String firstName, String lastName) {
        return personRepository.deletePerson(firstName, lastName);
    }



    public List<Person> getChild(List<Person> personList) {
        List<Person> childList = new ArrayList<>();

        for (Person person : personList) {
            Period age = medicalRecordRepository.getAge(person);
            if (age.getYears() < 18) {
                childList.add(person);
            }
        }
        return childList;
    }

    private List<Person> getFamily(Person person) {
        List<Person> personList = new ArrayList<>(personRepository.getPersonMap().values());
        List<Person> family = new ArrayList<>();

        for (Person person1 : personList) {
            if (person.getLastName().equals(person1.getLastName()) && !(person.getFirstName().equals(person1.getFirstName()))) {
                if (person.getAddress().equals(person1.getAddress())) {
                    family.add(person1);
                }
            }
        }
        return family;
    }
}