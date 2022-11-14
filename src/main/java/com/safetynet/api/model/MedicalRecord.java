package com.safetynet.api.model;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class MedicalRecord {

    private String firstName;
    private String lastName;
    private String birthdate;
    private Map<String, String> medications;
    private List<String> allergies;


    public MedicalRecord(String firstName, String lastName, String birthdate, Map<String, String> medications, List<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.medications = medications;
        this.allergies = allergies;
    }
}