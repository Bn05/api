package com.safetynet.api.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.api.model.Person;
import lombok.Data;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Repository
@Data
public class PersonRepository {

    private Map<String, Person> personMap = new HashMap<>();


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

    public String createPerson(Person person) {

        personMap.put(person.getFirstName() + person.getLastName(), person);

        //TODO : erreur si personne deja existante

        return "Person create !!";
    }

    public String updatePerson(Person person) {

        personMap.put(person.getFirstName() + person.getLastName(), person);

        //TODO : erreur si personne non existante

        return "Person update !!";

    }

    public String deletePerson(String firstName, String lastName) {

        personMap.remove(firstName + lastName);

        //TODO : erreur si personne non existante

        return "Person delete !!";
    }

}

