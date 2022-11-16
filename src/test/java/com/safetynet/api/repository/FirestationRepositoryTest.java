package com.safetynet.api.repository;

import com.safetynet.api.model.Firestation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FirestationRepositoryTest {

    @Autowired
    FirestationRepository firestationRepository;


    @Test
    void contextLoads() {
    }

    @Test
    void readJsonFile() {
    }

    @Test
    void createFirestationTest() {

        Firestation firestation = new Firestation("testAddress0", 0);

        firestationRepository.createFirestation(firestation);
        Firestation firestationTest= firestationRepository.getFirestationMap().get("testAddress0");

        assertEquals(firestation, firestationTest);

    }

    @Test
    void deleteFirestation() {
        Firestation firestation = new Firestation("testAddress0", 0);





    }

    @Test
    void updateFiresation() {
    }

    @Test
    void getFirestationByAddress() {
    }

    @Test
    void getFirestationsByNumber() {
    }
}