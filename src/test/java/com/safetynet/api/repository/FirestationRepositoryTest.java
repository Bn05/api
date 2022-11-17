package com.safetynet.api.repository;

import com.safetynet.api.model.Firestation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class FirestationRepositoryTest {

    @Autowired
    FirestationRepository firestationRepository;
    private Map<String, Firestation> testMap = new HashMap<>();
    private Firestation firestation0 = new Firestation("testAddress0", 0);
    private Firestation firestation1 = new Firestation("testAddress1", 1);

    @BeforeEach
    public void setUpPerTest() {
        testMap.clear();
        ReflectionTestUtils.setField(firestationRepository, "firestationMap", testMap);
        testMap.put(firestation0.getAddress(), firestation0);
    }

    @Test
    void contextLoads() {
    }

    @Test
    void createFirestationTest() {

        String result = firestationRepository.createFirestation(firestation1);
        Firestation firestationTest = testMap.get("testAddress1");

        assertEquals(firestation1, firestationTest);
        assertEquals("Firestation add !", result);
    }

    @Test
    void createFirestationAlreadyExistTest() {

        String result = firestationRepository.createFirestation(firestation0);

        assertEquals("Firestation already exist", result);
    }

    @Test
    void deleteFirestationTest() {

        String result = firestationRepository.deleteFirestation(firestation0);

        boolean test = testMap.containsValue(firestation0);

        assertFalse(test);
        assertEquals("Firestation delete !", result);
    }

    @Test
    void deleteFirestationNoExistTest() {

        String result = firestationRepository.deleteFirestation(firestation1);
        boolean test = testMap.containsValue(firestation1);

        assertEquals("Firestation doesn't exist !", result);
        assertFalse(test);
    }

    @Test
    void updateFiresationTest() {


        Firestation firestationUpdate = new Firestation("testAddress0", 1);

        String result = firestationRepository.updateFiresation(firestationUpdate);
        boolean test = testMap.containsValue(firestation0);
        boolean test2 = testMap.containsValue(firestationUpdate);

        assertEquals("Firestation update !", result);
        assertFalse(test);
        assertTrue(test2);

    }

    @Test
    void updateFiresationNoExistTest() {

        String result = firestationRepository.updateFiresation(firestation1);
        boolean test = testMap.containsValue(firestation1);


        assertEquals("Firestation doesn't exist !", result);
        assertFalse(test);

    }

    @Test
    void getFirestationByAddressTest() {

        Firestation firestationResult0 = firestationRepository.getFirestationByAddress("testAddress0");

        assertEquals(firestation0, firestationResult0);
    }

    @Test
    void getFirestationByAddressNoExistTest() {

        Firestation firestationResult = firestationRepository.getFirestationByAddress("Error Address");

        assertNull(firestationResult);
    }

    @Test
    void getFirestationsByNumberTest() {

        List<Firestation> firestationList = new ArrayList<>();
        firestationList.add(firestation0);

        List<Firestation> firestationResult = firestationRepository.getFirestationsByNumber(0);

        assertEquals(firestationList, firestationResult);
    }

    @Test
    void getFirestationsByNumberNoExistTest() {

        List<Firestation> firestationResult0 = firestationRepository.getFirestationsByNumber(1);

        assertTrue(firestationResult0.isEmpty());
    }



}


