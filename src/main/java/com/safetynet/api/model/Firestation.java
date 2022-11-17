package com.safetynet.api.model;

public class Firestation {

    private String address;
    private int station;

    public Firestation(String address, int station) {
        this.address = address;
        this.station = station;
    }

    public int getStation() {
        return station;
    }

    public String getAddress() {
        return address;
    }
}
