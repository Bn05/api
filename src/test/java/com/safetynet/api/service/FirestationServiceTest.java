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

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class FirestationServiceTest {


    @InjectMocks
    FirestationService firestationService;

    @Mock
    FirestationRepository firestationRepository;
    @Mock
    PersonRepository personRepository;
    @Mock
    MedicalRecordRepository medicalRecordRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getPersonsByStationNumber() {

        int stationNumber = 0;
        Firestation firestation = new Firestation("address0", 0);
        List<Firestation> firestationServiceList = new ArrayList<>();
        firestationServiceList.add(firestation);
        when(firestationRepository.getFirestationsByNumber(stationNumber)).thenReturn(firestationServiceList);


        Person person0 = new Person("firstName0", "lastName0", "address0", "city0", 0, "phone0", "0@0");
        List<Person> personSelecListMock = new ArrayList<>();
        personSelecListMock.add(person0);

        when(personRepository.getPersonByStation(firestation)).thenReturn(personSelecListMock);

        Map<String, Integer> countAdultAndChildMapMock = new HashMap<>();
        countAdultAndChildMapMock.put("Adult", 0);
        countAdultAndChildMapMock.put("Child", 1);

        when(medicalRecordRepository.countAdultAndChild(personSelecListMock)).thenReturn(countAdultAndChildMapMock);

        Map<String, String> personMap = new HashMap<>();
        personMap.put("firstName", "firstName0");
        personMap.put("lastName", "lastName0");
        personMap.put("address", "address0");
        personMap.put("phone", "phone0");
        List<Object> personLiteSelectListMock = new ArrayList<>();
        personLiteSelectListMock.add(personMap);


        List<Object> resultList = new ArrayList<>();
        resultList.add(countAdultAndChildMapMock);
        resultList.add(personLiteSelectListMock);

        List<Object> testList = firestationService.getPersonsByStationNumber(stationNumber);

        assertEquals(resultList, testList);
    }

    @Test
    void getPhoneNumberByStationNumber() {
        int stationNumber = 0;
        List<String> phoneListResult = new ArrayList<>();

        Firestation firestation = new Firestation("address0", 0);
        List<Firestation> firestationServiceList = new ArrayList<>();
        firestationServiceList.add(firestation);
        when(firestationRepository.getFirestationsByNumber(stationNumber)).thenReturn(firestationServiceList);

        Person person0 = new Person("firstName0", "lastName0", "address0", "city0", 0, "phone0", "0@0");
        List<Person> personSelecListMock = new ArrayList<>();
        personSelecListMock.add(person0);
        when(personRepository.getPersonByStation(firestation)).thenReturn(personSelecListMock);

        phoneListResult.add("phone0");
        List<String> phoneListTest = firestationService.getPhoneNumberByStationNumber(stationNumber);

        assertEquals(phoneListResult, phoneListTest);
    }

    @Test
    void getFamilyByFireStation() {
        List<Integer> firestationNumberList = new ArrayList<>();
        firestationNumberList.add(0);

        Map<String, List<String>> result = new HashMap<>();


        Firestation firestation = new Firestation("address0", 0);
        List<Firestation> firestationServiceList = new ArrayList<>();
        firestationServiceList.add(firestation);
        when(firestationRepository.getFirestationsByNumber(firestationNumberList.get(0))).thenReturn(firestationServiceList);

        Person person0 = new Person("firstName0", "lastName0", "address0", "city0", 0, "phone0", "0@0");
        List<Person> personSelecListMock = new ArrayList<>();
        personSelecListMock.add(person0);
        when(personRepository.getPersonByStation(firestation)).thenReturn(personSelecListMock);

        Period period = Period.of(30, 1, 25);
        when(medicalRecordRepository.getAge(person0)).thenReturn(period);

        Map<String, String> medicationMap0 = new HashMap<>();
        medicationMap0.put("medication00", "medic00");
        medicationMap0.put("medication01", "medic01");
        List<String> allergieMap0 = new ArrayList<>();
        allergieMap0.add("allergie0");
        LocalDate birthdate0 = LocalDate.of(1992, 3, 10);
        MedicalRecord medicalRecord0 = new MedicalRecord("firstName0", "lastName0", birthdate0, medicationMap0, allergieMap0);


        when(medicalRecordRepository.getMedicalRecord("firstName0", "lastName0")).thenReturn(medicalRecord0);
        when(medicalRecordRepository.getMedicalRecord("firstName0", "lasName0")).thenReturn(medicalRecord0);

        List<String> personStringMock = new ArrayList<>();
        personStringMock.add("firstName0" + " " + "lastName0" + " || Phone : " + "phone0" + " || Age : " + 30 + " || Medications : " + medicationMap0 + " || Allergies : " + allergieMap0);

        result.put("Address : " + firestation.getAddress(), personStringMock);

        Map<String, List<String>> test = firestationService.getFamilyByFireStation(firestationNumberList);

        assertEquals(result, test);
    }

    @Test
    void createFirestationTest() {

        Firestation firestation = new Firestation("address0", 0);
        firestationService.createFirestation(firestation);

        verify(firestationRepository, times(1)).createFirestation(any());
    }

    @Test
    void deleteFirestation() {
        Firestation firestation = new Firestation("address0", 0);
        firestationService.deleteFirestation(firestation);

        verify(firestationRepository, times(1)).deleteFirestation(any());
    }

    @Test
    void updateFiresation() {
        Firestation firestation = new Firestation("address0", 0);
        firestationService.updateFiresation(firestation);

        verify(firestationRepository, times(1)).updateFiresation(any());
    }
}