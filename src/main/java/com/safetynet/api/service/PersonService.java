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

import java.time.Period;
import java.util.*;

@Service
public class PersonService {

    Logger logger = LoggerFactory.getLogger(PersonService.class);
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;
    @Autowired
    private FirestationRepository firestationRepository;

    public List<Object> getChildAlertAndFamilyByAddress(String address) {
        logger.debug("Call personService.getChildAlertAndFamilyByAddress with param : " + address);

        List<Person> personList = personRepository.getPersonByAddress(address);
        logger.debug("Response PersonRepository.getPersonByAddress = " + personList.toString());

        List<Person> childList = getChild(personList);
        logger.debug("Response to PersonService.getChild : " + childList);


        List<Object> childInfoList = new ArrayList<>();
        for (Person child : childList) {
            List<Person> family = getFamily(child);
            logger.debug("Response to PersonService.getFamily : " + family.toString());

            List<Object> familyLite = new ArrayList<>();
            for (Person familymember : family) {
                Map<String, String> member = new HashMap<>();
                member.put("firstName", familymember.getFirstName());
                member.put("lastName", familymember.getLastName());
                familyLite.add(member);
            }

            logger.debug(" familyLite = " + familyLite);

            Map<String, Object> childMap = new LinkedHashMap<>();
            childMap.put("FirstName", child.getFirstName());
            childMap.put("LastName", child.getLastName());
            childMap.put("Age", medicalRecordRepository.getAge(child).getYears());
            childMap.put("Family", familyLite);

            logger.debug("childMap = " + childMap);
            childInfoList.add(childMap);
        }

        return childInfoList;


    }

    public Map<String, Object> getPersonMedicalRecordAndStationNumber(String address) {

        logger.debug("Call PersonService.getPersonMedicalRecordAndStationNumber with param = " + address);

        List<Person> personList = personRepository.getPersonByAddress(address);
        logger.debug("Reponse to personRepository.getPersonByAddress : " + personList.toString());

        Firestation firestation = firestationRepository.getFirestationByAddress(address);
        logger.debug("Response to firestationRepository.getFirestationByAddress with param = " + address);

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
            logger.debug("personMap = "+personMap);
        }

        Map<String, Object> objectMap = new LinkedHashMap<>();

        objectMap.put("firesation", firestation.getStation());
        objectMap.put("person", personLiteList);

        return objectMap;
    }

    public List<String> getEmailByCity(String city) {
        logger.debug("Call PersonService.getEmailByCity with param : "+city);
        List<String> returnListString = personRepository.getEmailByCity(city);
        logger.debug("Response to personRepository.getEmailByCity : "+returnListString.toString());
        return returnListString;
    }

    public List<Map<String, String>> getPersonInfo(String firstName, String lastNAme) {

        logger.debug("Call PersonService.getPersonInfo with param = "+firstName+","+lastNAme);

        List<Person> personList = new ArrayList<>(personRepository.getPersonMap().values());

        List<Map<String, String>> peronResultList = new ArrayList<>();
        for (Person person : personList) {
            if (person.getLastName().equals(lastNAme) && person.getFirstName().equals(firstName)) {
                Map<String, String> personMap = new LinkedHashMap<>();
                personMap.put("firstName", person.getFirstName());
                personMap.put("lastName", person.getLastName());
                personMap.put("address", person.getAddress());
                personMap.put("Age", String.valueOf(medicalRecordRepository.getAge(person).getYears()));
                personMap.put("email", person.getEmail());
                personMap.put("medications", medicalRecordRepository.getMedication(firstName, lastNAme).toString());
                personMap.put("allergies", medicalRecordRepository.getAllergie(firstName, lastNAme).toString());
                peronResultList.add(personMap);
            }
        }
        return peronResultList;
    }

    public String createPerson(Person person) {
        logger.debug("Call personService.createPerson() with param : " + person);
        return personRepository.createPerson(person);
    }

    public String updatePerson(Person person) {

        logger.debug("Call personService.updatePerson() with param : " + person);
        return personRepository.updatePerson(person);
    }

    public String deletePerson(String firstName, String lastName) {
        logger.debug("Call personService.deletePerson() with param : " + firstName + "," + lastName);
        return personRepository.deletePerson(firstName, lastName);
    }

    public List<Person> getChild(List<Person> personList) {
        logger.debug("Call PersonService.getChild with param = " + personList.toString());

        List<Person> childList = new ArrayList<>();
        for (Person person : personList) {
            Period age = medicalRecordRepository.getAge(person);
            logger.debug("Response to medicalRecordRepository.getAge : " + age.getYears());
            if (age.getYears() < 18) {
                childList.add(person);
            }
        }
        return childList;
    }

    private List<Person> getFamily(Person person) {
        logger.debug("Call PersonService.getFamily with param : " + person.toString());
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