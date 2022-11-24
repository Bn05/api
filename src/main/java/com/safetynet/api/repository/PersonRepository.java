package com.safetynet.api.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.Error.ErrorAlreadyExistException;
import com.safetynet.api.Error.ErrorNoExistException;
import com.safetynet.api.model.Firestation;
import com.safetynet.api.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class PersonRepository {

    Logger logger = LoggerFactory.getLogger(PersonRepository.class);

    private Map<String, Person> personMap = new HashMap<>();

    public Map<String, Person> getPersonMap() {

        return new HashMap<>(personMap);

    }

    public void readJsonFile() throws IOException {

        ArrayList<Person> personList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode persons = objectMapper.readTree(new File("src/main/resources/data.json")).get("persons");

        for (JsonNode person : persons) {
            personList.add(new Person(person.path("firstName").asText(), person.path("lastName").asText(), person.path("address").asText(), person.path("city").asText(), person.path("zip").asInt(), person.path("phone").asText(), person.path("email").asText()));
        }

        for (Person person : personList) {
            personMap.put(person.getFirstName() + person.getLastName(), person);
        }
    }

    /**
     * Add Person to the List
     * @param person
     * @return "Person create !!" if creation is successful or "Person Already Exist !"
     */
    public String createPerson(Person person) {
        logger.debug("Call PersonRepository.createPerson with parma = " + person.toString());
        try {
            if (!personMap.containsValue(person)) {
                personMap.put(person.getFirstName() + person.getLastName(), person);
                return "Person create !!";
            } else {
                throw new ErrorAlreadyExistException("Person Already Exist !");
            }
        } catch (ErrorAlreadyExistException e) {
            logger.error("Person Already Exist !");
            return "Person Already Exist !";
        }


    }

    /**
     * Update person to the List
     * @param person
     * @return "Person update !!" if update is successful or "Person doesn't Exist !"
     */
    public String updatePerson(Person person) {
        logger.debug("Call PersonRepository.updatePerson with parma = " + person.toString());
        try {
            if (personMap.containsKey(person.getFirstName() + person.getLastName())) {
                personMap.put(person.getFirstName() + person.getLastName(), person);
                return "Person update !!";
            } else {
                throw new ErrorNoExistException("Person doesn't Exist !");
            }
        } catch (ErrorNoExistException e) {
            logger.error("Person doesn't Exist !");
            return "Person doesn't Exist !";
        }
    }

    /**
     * Delete person to the List
     * @param firstName
     * @param lastName
     * @return "Person delete !!" if deletion is successful or "Person doesn't Exist !"
     */
    public String deletePerson(String firstName, String lastName) {
        logger.debug("Call PersonRepository.updatePerson with parma = " + firstName + "," + lastName);
        try {
            if (personMap.containsKey(firstName + lastName)) {
                personMap.remove(firstName + lastName);
                return "Person delete !!";
            } else {
                throw new ErrorNoExistException("Person doesn't Exist !");
            }
        } catch (ErrorNoExistException e) {
            logger.error("Person doesn't Exist !");
            return "Person doesn't Exist !";
        }
    }

    /**
     * Give Person by station Number
     * @param firestation
     * @return List<Person>
     */
    public List<Person> getPersonByStation(Firestation firestation) {
        logger.debug("Call personRepository.getPersonByStation with param = " + firestation.toString());
        List<Person> personList = new ArrayList<>(personMap.values());
        List<Person> personSelectList = new ArrayList<>();

        for (Person person : personList) {
            if (person.getAddress().equals(firestation.getAddress())) {
                personSelectList.add(person);
            }
        }

        return personSelectList;
    }

    /**
     * Give Person by address
     * @param address
     * @return List<Person>
     */
    public List<Person> getPersonByAddress(String address) {
        logger.debug("Call PersonRepository.getPersonByAddress with param = " + address);
        List<Person> personList = new ArrayList<>(personMap.values());
        List<Person> personSelectList = new ArrayList<>();
        for (Person person : personList) {
            if (address.equals(person.getAddress())) {
                personSelectList.add(person);
            }
        }
        return personSelectList;
    }

    /**
     * Give email's person who lives in this city
     * @param city
     * @return List<String>
     */
    public List<String> getEmailByCity(String city) {
        logger.debug("Call personRepository.getEmailByCity with param : " + city);
        List<Person> personList = new ArrayList<>(personMap.values());

        List<String> emailList = new ArrayList<>();
        for (Person person : personList) {
            if (person.getCity().equals(city)) {
                emailList.add(person.getEmail());
            }
        }
        return emailList;
    }
}

