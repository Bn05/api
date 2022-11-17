package com.safetynet.api.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MedicalRecord {

    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private Map<String, String> medications;
    private List<String> allergies;


    public MedicalRecord(String firstName, String lastName, LocalDate birthdate, Map<String, String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public Map<String, String> getMedications() {
        return new HashMap<>(medications);
    }

    public List<String> getAllergies() {
        return new ArrayList<>(allergies);
    }
}