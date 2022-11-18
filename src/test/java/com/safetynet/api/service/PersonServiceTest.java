package com.safetynet.api.service;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.FirestationRepository;
import com.safetynet.api.repository.MedicalRecordRepository;
import com.safetynet.api.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Period;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
class PersonServiceTest {

    @InjectMocks
    PersonService personService;
    @Mock
    PersonRepository personRepository;
    @Mock
    MedicalRecordRepository medicalRecordRepository;
    @Mock
    FirestationRepository firestationRepository;


    @BeforeEach
    void setUp() {
    }

    @Test
    void getChildAlertAndFamilyByAddress() {
        String address = "address0";


        Person person0 = new Person("firstName0", "lastName0", "address0", "city0", 0, "phone0", "0@0");
        Person child0 = new Person("childFirstName0", "lastName0", "address0", "city0", 0, "phone0", "0@0");
        Person person1 = new Person("firstName1", "lastName1", "address1", "city1", 1, "phone1", "1@1");
        Person person2 = new Person("firstName2", "lastName0", "address2", "city0", 0, "phone0", "0@0");
        List<Person> personList = new ArrayList<>();
        personList.add(child0);
        personList.add(person0);
        personList.add(person1);
        personList.add(person2);

        Map<String, Person> personMap = new HashMap<>();
        for (Person person : personList) {
            personMap.put(person.getFirstName() + person.getLastName(), person);
        }


        when(personRepository.getPersonByAddress(address)).thenReturn(personList);
        when(medicalRecordRepository.getAge(child0)).thenReturn(Period.of(5, 1, 25));
        when(medicalRecordRepository.getAge(person0)).thenReturn(Period.of(45, 1, 25));
        when(medicalRecordRepository.getAge(person1)).thenReturn(Period.of(50, 1, 25));
        when(medicalRecordRepository.getAge(person2)).thenReturn(Period.of(45, 1, 25));

        when(personRepository.getPersonMap()).thenReturn(personMap);


        List<Object> test = personService.getChildAlertAndFamilyByAddress(address);


        Map<String, String> memberMap = new HashMap<>();
        memberMap.put("firstName", "firstName0");
        memberMap.put("lastName", "lastName0");

        List<Object> familyLite = new ArrayList<>();
        familyLite.add(memberMap);

        Map<String, Object> childMap = new LinkedHashMap<>();
        childMap.put("FirstName", "childFirstName0");
        childMap.put("LastName", "lastName0");
        childMap.put("Age", Period.of(5, 1, 25).getYears());
        childMap.put("Family", familyLite);

        List<Object> resultMap = new ArrayList<>();

        resultMap.add(childMap);


        assertEquals(resultMap, test);


    }

    @Test
    void getPersonMedicalRecordAndStationNumber() {
        String address = "address0";

        Person person0 = new Person("firstName0", "lastName0", "address0", "city0", 0, "phone0", "0@0");


        Firestation firestation = new Firestation("address0", 0);

        List<Person> personList = new ArrayList<>();
        personList.add(person0);

        when(personRepository.getPersonByAddress(address)).thenReturn(personList);
        when(firestationRepository.getFirestationByAddress(address)).thenReturn(firestation);

        List<String> allergieList = new ArrayList<>();
        allergieList.add("allergieTest");
        Map<String, String> medicationMap = new HashMap<>();
        medicationMap.put("medocTest", "30KG!!");

        MedicalRecord medicalRecord = new MedicalRecord(null, null, null, medicationMap, allergieList);

        when(medicalRecordRepository.getMedicalRecord(any(), any())).thenReturn(medicalRecord);

        when(medicalRecordRepository.getAge(any())).thenReturn(Period.of(45, 1, 25));

        List<Object> personLiteList = new ArrayList<>();


        Map<String, Object> personMap = new LinkedHashMap<>();
        personMap.put("firstName", "firstName0");
        personMap.put("lastName", "lastName0");
        personMap.put("phone", "phone0");
        personMap.put("age", Period.of(45, 1, 25).getYears());
        personMap.put("allergies", allergieList);
        personMap.put("medications", medicationMap);
        personLiteList.add(personMap);


        Map<String, Object> resultMap = new LinkedHashMap<>();
        resultMap.put("firesation", 0);
        resultMap.put("person", personLiteList);

        Map<String, Object> testMap = personService.getPersonMedicalRecordAndStationNumber(address);

        assertEquals(resultMap, testMap);
    }

    @Test
    void getEmailByCity() {
        personRepository.getEmailByCity(any());

        verify(personRepository, times(1)).getEmailByCity(any());
    }

    @Test
    void getPersonInfo() {
        Person person0 = new Person("firstName0", "lastName0", "address0", "city0", 0, "phone0", "00@");
        Map<String, Person> personMap = new HashMap<>();
        personMap.put("firstName0lastName0", person0);


        when(personRepository.getPersonMap()).thenReturn(personMap);
        when(medicalRecordRepository.getAge(any())).thenReturn(Period.of(45, 1, 25));

        List<String> allergieList = new ArrayList<>();
        Map<String, String> medicationMap = new HashMap<>();

        Map<String, String> personInfoMap = new LinkedHashMap<>();
        personInfoMap.put("firstName", "firstName0");
        personInfoMap.put("lastName", "lastName0");
        personInfoMap.put("address", "address0");
        personInfoMap.put("Age", "45");
        personInfoMap.put("email", "00@");
        personInfoMap.put("medications", medicationMap.toString());
        personInfoMap.put("allergies", allergieList.toString());

        List<Map<String, String>> peronResultList = new ArrayList<>();
        peronResultList.add(personInfoMap);

        List<Map<String, String>> peronTesttList = personService.getPersonInfo("firstName0", "lastName0");


        assertEquals(peronResultList, peronTesttList);


    }

    @Test
    void createPerson() {
        personRepository.createPerson(any());

        verify(personRepository, times(1)).createPerson(any());
    }

    @Test
    void updatePerson() {
        personRepository.updatePerson(any());

        verify(personRepository, times(1)).updatePerson(any());
    }

    @Test
    void deletePerson() {
        personRepository.deletePerson(any(),any());

        verify(personRepository, times(1)).deletePerson(any(),any());
    }
}