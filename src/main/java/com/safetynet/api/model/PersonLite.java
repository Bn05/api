package com.safetynet.api.model;

import lombok.Data;

@Data
public class PersonLite {

    private String firstName;
    private String lastName;
    private String address;
    private String phone;

    public PersonLite(String firstName, String lastName, String address, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phone = phone;
    }

}