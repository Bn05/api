package com.safetynet.api.repository;

import com.safetynet.api.Error.ErrorAlwaysExistException;
import com.safetynet.api.model.Firestation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FirestationRepositoryTest {

    @Autowired
    FirestationRepository firestationRepository;


    @BeforeEach
    void setUpPerTest() {
        firestationRepository.getFirestationMap().clear();

    }

    @Test
    void contextLoads() {
    }

    @Test
    void readJsonFile() {
    }

    @Test
    void createFirestationTest() {

        Firestation firestation = new Firestation("testAddress0", 0);

        String result = firestationRepository.createFirestation(firestation);
        Firestation firestationTest = firestationRepository.getFirestationMap().get("testAddress0");

        assertEquals(firestation, firestationTest);
        assertEquals("Firestation add !", result);
    }

    @Test
    void createFirestationAlwaysExistTest() {
        Firestation firestation = new Firestation("testAddress0", 0);

        firestationRepository.createFirestation(firestation);

        String result = firestationRepository.createFirestation(firestation);

        assertEquals("Firestation always exist", result);
    }

    @Test
    void deleteFirestationTest() {

        Firestation firestation = new Firestation("testAddress0", 0);
        Map<String, Firestation> firestationMap = firestationRepository.getFirestationMap();
        firestationMap.put(firestation.getAddress(), firestation);

        String result = firestationRepository.deleteFirestation(firestation);

        boolean test = firestationMap.containsKey("testAddress0");

        assertFalse(test);
        assertEquals("Firestation delete !", result);
    }

    @Test
    void deleteFirestationNoExistTest() {

        Firestation firestation = new Firestation("testAddress0", 0);

        String result = firestationRepository.deleteFirestation(firestation);

        assertEquals("Firestation doesn't exist !", result);
    }

    @Test
    void updateFiresationTest() {
        Firestation firestation = new Firestation("testAddress0", 0);
        Firestation firestationUpdate = new Firestation("testAddress0", 1);
        Map<String, Firestation> firestationMap = firestationRepository.getFirestationMap();
        firestationMap.put(firestation.getAddress(), firestation);

        String result = firestationRepository.updateFiresation(firestationUpdate);
        boolean test = firestationMap.containsValue(firestation);

        assertFalse(test);
        assertEquals("Firestation update !", result);

    }

    @Test
    void updateFiresationNoExistTest() {
        Firestation firestationUpdate = new Firestation("testAddress0", 1);
        Map<String, Firestation> firestationMap = firestationRepository.getFirestationMap();

        String result = firestationRepository.updateFiresation(firestationUpdate);
        boolean test = firestationMap.containsValue(firestationUpdate);


        assertEquals("Firestation doesn't exist !", result);
        assertFalse(test);

    }

    @Test
    void getFirestationByAddressTest() {
        Firestation firestation0 = new Firestation("testAddress0", 0);
        Map<String, Firestation> firestationMap = firestationRepository.getFirestationMap();
        firestationMap.put(firestation0.getAddress(), firestation0);

        Firestation firestationResult0 = firestationRepository.getFirestationByAddress("testAddress0");

        assertEquals(firestation0, firestationResult0);
    }

    @Test
    void getFirestationByAddressNoExistTest() {
        Firestation firestation0 = new Firestation("testAddress0", 0);
        Map<String, Firestation> firestationMap = firestationRepository.getFirestationMap();
        firestationMap.put(firestation0.getAddress(), firestation0);

        Firestation firestationResult = firestationRepository.getFirestationByAddress("Error Address");

        assertNull(firestationResult);
    }

    @Test
    void getFirestationsByNumberTest() {

        Firestation firestation0 = new Firestation("testAddress0", 0);
        List<Firestation> firestationList = new ArrayList<>();
        firestationList.add(firestation0);
        Map<String, Firestation> firestationMap = firestationRepository.getFirestationMap();
        firestationMap.put(firestation0.getAddress(), firestation0);

        List<Firestation> firestationResult0 = firestationRepository.getFirestationsByNumber(0);

        assertEquals(firestationList, firestationResult0);
    }

    @Test
    void getFirestationsByNumberNoExistTest() {

        Firestation firestation0 = new Firestation("testAddress0", 0);
        Map<String, Firestation> firestationMap = firestationRepository.getFirestationMap();
        firestationMap.put(firestation0.getAddress(), firestation0);

        List<Firestation> firestationResult0 = firestationRepository.getFirestationsByNumber(1);

        assertTrue(firestationResult0.isEmpty());


    }
}