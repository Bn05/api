package com.safetynet.api.service;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.model.Person;
import com.safetynet.api.repository.FirestationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Service
public class FirestationService {

    @Autowired
    private FirestationRepository firestationRepository;
    @Autowired
    private PersonService personService;

    public List<Object> getPersonsByStationNumber(int stationNumber) {

        List<Firestation> firestationSelectList = getFirestationsByNumber(stationNumber);

        List<Person> personSelectList = new ArrayList<>();

        for (Firestation firestation : firestationSelectList) {
            personSelectList.addAll(personService.getPersonByStation(firestation));
        }

        Map<String, Integer> countAdultAndChildMap = personService.countAdultAndChild(personSelectList);
        List<Object> personLiteSelectList = personService.personToPersonLite(personSelectList);

        return Arrays.asList(countAdultAndChildMap, personLiteSelectList);
    }

    public String createFirestation(Firestation firestation) {
        return firestationRepository.createFirestation(firestation);
    }

    public String deleteFirestation(String firestation) {

        return firestationRepository.deleteFirestation(firestation);
    }

    public String updateFiresation(Firestation firestation) {
        return firestationRepository.updateFiresation(firestation);
    }

    private List<Firestation> getFirestationsByNumber(int stationNumber) {

        List<Firestation> firestationList = new ArrayList<>(firestationRepository.getFirestationMap().values());
        List<Firestation> firestationSelectList = new ArrayList<>();
        for (Firestation firestation : firestationList) {
            if (firestation.getStation() == stationNumber) {
                firestationSelectList.add(firestation);
            }
        }
        return firestationSelectList;
    }

}
