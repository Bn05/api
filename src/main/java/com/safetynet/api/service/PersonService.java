package com.safetynet.api.service;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MedicalRecordService medicalRecordService;

    public String createPerson(Person person) {
        return personRepository.createPerson(person);
    }

    public String updatePerson(Person person) {
        return personRepository.updatePerson(person);
    }

    public String deletePerson(String firstName, String lastName) {
        return personRepository.deletePerson(firstName, lastName);
    }

    public List<Person> getPersonByStation(Firestation firestation) {

        List<Person> personList = new ArrayList<>(personRepository.getPersonMap().values());
        List<Person> personSelectList = new ArrayList<>();

        for (Person person : personList) {
            if (person.getAddress().equals(firestation.getAddress())) {
                personSelectList.add(person);
            }
        }

        return personSelectList;
    }

    public Map<String, Integer> countAdultAndChild(List<Person> personList) {

        Map<String, Integer> listCount = new HashMap<>();
        listCount.put("Adult", 0);
        listCount.put("Children", 0);
        LocalDate now = LocalDate.now();

        for (Person person : personList) {
            LocalDate birthdate = medicalRecordService.getMedicalRecord(person.getFirstName(), person.getLastName()).getBirthdate();
            Period age = Period.between(birthdate, now);

            if (age.getYears() > 18) {
                listCount.put("Adult", listCount.get("Adult") + 1);
            } else {
                listCount.put("Children", listCount.get("Children") + 1);
            }
        }
        return listCount;
    }

    public List<Object> personToPersonLite(List<Person> personList) {

        List<Object> personLiteList = new ArrayList<>();

        for (Person person : personList) {
            Map<String, String> personMap = new HashMap<>();
            personMap.put("firstName", person.getFirstName());
            personMap.put("lastName", person.getLastName());
            personMap.put("adress", person.getAddress());
            personMap.put("phone", person.getPhone());
            personLiteList.add(personMap);
        }

        return personLiteList;
    }


}