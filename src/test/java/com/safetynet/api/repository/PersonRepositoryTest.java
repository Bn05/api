package com.safetynet.api.repository;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;
    private Map<String, Person> personMap = new HashMap<>();
    private Person person0 = new Person("firstName0", "lastName0", "address0", "city0", 0, "phone0", "0@0");
    private String key0 = person0.getFirstName() + person0.getLastName();
    private Person person1 = new Person("firstName1", "lastName1", "address1", "city1", 1, "phone1", "1@1");
    private String key1 = person1.getFirstName() + person1.getLastName();

    @BeforeEach
    void setUp() {
        personMap.clear();
        ReflectionTestUtils.setField(personRepository, "personMap", personMap);
        personMap.put(key0, person0);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void getPersonMapTest() {
        Map<String, Person> mapTest = personRepository.getPersonMap();

        assertEquals(personMap,mapTest);
    }

    @Test
    void createPersonTest() {

        String result = personRepository.createPerson(person1);
        Person personTest = personMap.get(key1);

        assertEquals("Person create !!", result);
        assertEquals(person1, personTest);
    }

    @Test
    void createPersonAlreadyExistTest() {

        String result = personRepository.createPerson(person0);
        Person personTest = personMap.get(key0);

        assertEquals("Person Already Exist !", result);
        assertEquals(person0, personTest);
    }

    @Test
    void updatePersonTest() {
        Person personUpdate = new Person("firstName0", "lastName0", "address1", "city1", 1, "phone1", "1@1");

        String result = personRepository.updatePerson(personUpdate);
        Person personTest = personMap.get(key0);

        assertEquals("Person update !!", result);
        assertEquals(personUpdate, personTest);
    }

    @Test
    void updatePersonNoExistTest() {

        String result = personRepository.updatePerson(person1);
        Person personTest = personMap.get(key1);

        assertEquals("Person doesn't Exist !", result);
        assertNull(personTest);
    }

    @Test
    void deletePersonTest() {

        String result = personRepository.deletePerson(person0.getFirstName(), person0.getLastName());
        Person personTest = personMap.get(key0);

        assertEquals("Person delete !!", result);
        assertNull(personTest);
    }

    @Test
    void deletePersonNoExistTest() {
        String result = personRepository.deletePerson(person1.getFirstName(), person1.getLastName());

        assertEquals("Person doesn't Exist !", result);
    }

    @Test
    void getPersonByStationTest() {

        Firestation firestation = new Firestation("address0", 0);
        personMap.put(key1, person1);
        List<Person> personListResult = new ArrayList<>();
        personListResult.add(person0);

        List<Person> personListTest = personRepository.getPersonByStation(firestation);

        assertEquals(personListResult, personListTest);

    }

    @Test
    void getPersonByAddressTest() {

        String address = "address0";
        personMap.put(key1, person1);
        List<Person> personListResult = new ArrayList<>();
        personListResult.add(person0);

        List<Person> personListTest = personRepository.getPersonByAddress(address);

        assertEquals(personListResult, personListTest);

    }

    @Test
    void getEmailByCity() {

        String city = "city0";
        personMap.put(key1, person1);
        List<String> emailListResult = new ArrayList<>();
        emailListResult.add(person0.getEmail());

        List<String> emailListTest = personRepository.getEmailByCity(city);

        assertEquals(emailListResult, emailListTest);

    }
}