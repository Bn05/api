package com.safetynet.api.service;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MedicalRecordService medicalRecordService;

    public List<Object> childAlertAndFamilyByAdress(String adress) {

        List<Person> personList = getPersonByAdress(adress);
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
            childMap.put("Age", getAge(child).getYears());
            childMap.put("Family", familyLite);

            childInfoList.add(childMap);
        }

        return childInfoList;


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

    public List<Person> getPersonByAdress(String adress) {
        List<Person> personList = new ArrayList<>(personRepository.getPersonMap().values());
        List<Person> personSelectList = new ArrayList<>();

        for (Person person : personList) {
            if (adress.equals(person.getAddress())) {
                personSelectList.add(person);
            }
        }
        return personSelectList;
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

    public List<Person> getChild(List<Person> personList) {
        List<Person> childList = new ArrayList<>();

        for (Person person : personList) {
            Period age = getAge(person);
            if (age.getYears() < 18) {
                childList.add(person);
            }
        }
        return childList;
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

    public List<Object> personToPersonLite(List<Person> personList) {

        List<Object> personLiteList = new ArrayList<>();

        for (Person person : personList) {
            Map<String, String> personMap = new LinkedHashMap<>();
            personMap.put("firstName", person.getFirstName());
            personMap.put("lastName", person.getLastName());
            personMap.put("adress", person.getAddress());
            personMap.put("phone", person.getPhone());
            personLiteList.add(personMap);
        }

        return personLiteList;
    }

    private Period getAge(Person person) {

        LocalDate now = LocalDate.now();
        LocalDate birthdate = medicalRecordService.getMedicalRecord(person.getFirstName(), person.getLastName()).getBirthdate();
        return Period.between(birthdate, now);

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