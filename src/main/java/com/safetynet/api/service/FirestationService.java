package com.safetynet.api.service;

import com.safetynet.api.model.Firestation;
import com.safetynet.api.model.Person;
import com.safetynet.api.model.PersonLite;
import com.safetynet.api.repository.FirestationRepository;
import com.safetynet.api.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class FirestationService {

    @Autowired
    private FirestationRepository firestationRepository;
    @Autowired
    private PersonRepository personRepository;

    public List<PersonLite> getPersonsByStation(int i) {

        List<Firestation> firestationList = new ArrayList<>(firestationRepository.getFirestationMap().values());
        List<Person> personList = new ArrayList<>(personRepository.getPersonMap().values());

        List<Firestation> firestationSelectList = new ArrayList<>();

        for (Firestation firestation : firestationList) {
            if (firestation.getStation() == i) {
                firestationSelectList.add(firestation);
            }
        }

        List<Person> personSelectList = new ArrayList<>();

        for (Firestation firestation : firestationSelectList) {
            for (Person person : personList) {
                if (person.getAddress().equals(firestation.getAddress())) {
                    personSelectList.add(person);
                }


            }
        }

        List<PersonLite> personSelectListLite = new ArrayList<>();
        for (Person person : personSelectList) {
            personSelectListLite.add(
                    new PersonLite(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone())
            );
        }


        return personSelectListLite;
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

}
