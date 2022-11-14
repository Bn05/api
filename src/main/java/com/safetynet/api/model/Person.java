package com.safetynet.api.model;

import lombok.Data;

@Data
public class Person {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private int zip;
    private String phone;
    private String email;

    public Person(String firstName, String lastName, String address, String city, int zip, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }
}