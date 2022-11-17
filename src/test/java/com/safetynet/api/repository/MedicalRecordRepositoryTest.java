package com.safetynet.api.repository;

import com.safetynet.api.model.MedicalRecord;
import com.safetynet.api.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MedicalRecordRepositoryTest {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    private Map<String, MedicalRecord> medicalRecordMap = new HashMap<>();

    private Map<String, String> medicationMap0 = new HashMap<>();
    private Map<String, String> medicationMap1 = new HashMap<>();
    private List<String> allergieMap0 = new ArrayList<>();
    private List<String> allergieMap1 = new ArrayList<>();
    private LocalDate birthdate0 =  LocalDate.of(1992,3,10);
    private LocalDate birthdate1 = LocalDate.of(2022,3,10);
    private MedicalRecord medicalRecord0 = new MedicalRecord("firstName0", "lastName0", birthdate0, medicationMap0, allergieMap0);
    private MedicalRecord medicalRecord1 = new MedicalRecord("firstName1", "lastName1", birthdate1, medicationMap1, allergieMap1);
    private String key0 = medicalRecord0.getFirstName() + medicalRecord0.getLastName();
    private String key1 = medicalRecord1.getFirstName() + medicalRecord1.getLastName();

    @BeforeEach
    void setUpPerTest() {

        medicalRecordMap.clear();

        ReflectionTestUtils.setField(medicalRecordRepository, "medicalRecordMap", medicalRecordMap);


        medicalRecordMap.put(key0, medicalRecord0);

        medicationMap0.put("medication00", "medic00");
        medicationMap0.put("medication01", "medic01");
        allergieMap0.add("allergie0");

        medicationMap1.put("medication10", "medic10");
        medicationMap1.put("medication11", "medic11");
        allergieMap1.add("allergie1");

    }


    @Test
    void contextLoads() {
    }

    @Test
    void createMedicalRecordTest() {

        String result = medicalRecordRepository.createMedicalRecord(medicalRecord1);
        MedicalRecord medicalRecordTest = medicalRecordMap.get(key1);

        assertEquals("Medical Record Create !!", result);
        assertEquals(medicalRecord1, medicalRecordTest);
    }

    @Test
    void createMedicalRecordAlreadyExistTest() {

        String result = medicalRecordRepository.createMedicalRecord(medicalRecord0);
        MedicalRecord medicalRecordTest = medicalRecordMap.get(key0);

        assertEquals("Medical Record Already Exist !!", result);
        assertEquals(medicalRecord0, medicalRecordTest);
    }

    @Test
    void updateMedicalRecordTest() {

        medicalRecordMap.put(key1, medicalRecord0);
        MedicalRecord medicalRecordTest0 = medicalRecordMap.get(key1);

        assertEquals(medicalRecord0, medicalRecordTest0);

        String result = medicalRecordRepository.updateMedicalRecord(medicalRecord1);
        MedicalRecord medicalRecordTest1 = medicalRecordMap.get(key1);

        assertEquals("Medical Record Update !!", result);
        assertEquals(medicalRecord1, medicalRecordTest1);
    }

    @Test
    void updateMedicalRecordNoExistTest() {

        String result = medicalRecordRepository.updateMedicalRecord(medicalRecord1);
        MedicalRecord medicalRecordTest1 = medicalRecordMap.get(key1);

        assertEquals("Medical Record doesn't exist !", result);
        assertNull(medicalRecordTest1);
    }

    @Test
    void deleteMedicalRecordTest() {
        String result = medicalRecordRepository.deleteMedicalRecord(medicalRecord0.getFirstName(), medicalRecord0.getLastName());
        MedicalRecord medicalRecordTest = medicalRecordMap.get(key0);

        assertEquals("Medical Record Delete !!", result);
        assertNull(medicalRecordTest);
    }

    @Test
    void deleteMedicalRecordNoExistTest() {
        String result = medicalRecordRepository.deleteMedicalRecord(medicalRecord1.getFirstName(), medicalRecord1.getLastName());
        MedicalRecord medicalRecordTest = medicalRecordMap.get(key1);

        assertEquals("Medical Record doesn't exist !", result);
        assertNull(medicalRecordTest);
    }

    @Test
    void getMedicalRecord() {

        MedicalRecord medicalRecordTest = medicalRecordRepository.getMedicalRecord("firstName0", "lastName0");

        assertEquals(medicalRecord0, medicalRecordTest);
    }

    @Test
    void countAdultAndChild() {

        medicalRecordMap.put(key1,medicalRecord1);

        Person person0 = new Person("firstName0", "lastName0", "address0", "city0", 0, "phone0", "0@0");
        Person person1 = new Person("firstName1", "lastName1", "address1", "city1", 1, "phone1", "1@1");
        List<Person> personList = new ArrayList<>();
        personList.add(person0);
        personList.add(person1);

        Map<String, Integer> countMap = new HashMap<>();
        countMap.put("Adult", 1);
        countMap.put("Children", 1);

        Map<String, Integer> resultMap = medicalRecordRepository.countAdultAndChild(personList);

        assertEquals(countMap,resultMap);

    }

    @Test
    void getAllergie() {

        List<String> result = medicalRecordRepository.getAllergie(medicalRecord0.getFirstName(),medicalRecord0.getLastName());

        assertEquals(allergieMap0,result);
    }

    @Test
    void getMedication() {

        Map<String, String> result = medicalRecordRepository.getMedication(medicalRecord0.getFirstName(),medicalRecord0.getLastName());

        assertEquals(medicationMap0,result);
    }
}