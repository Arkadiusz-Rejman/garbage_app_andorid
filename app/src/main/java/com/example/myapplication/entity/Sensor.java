package com.example.myapplication.entity;

import java.time.LocalDate;
import java.util.List;


public class Sensor {

    private Long id;
    private int sensorValue;
    private String sensorMac;
    private String readingDate;


    private Client client;


    private List<Disposal> disposals;

    public Sensor() {
    }

    public Sensor(int sensorValue, String sensorMac, String readingDate, Client client) {
        this.sensorValue = sensorValue;
        this.sensorMac = sensorMac;
        this.readingDate = readingDate;
        this.client = client;
    }

    public Sensor(int sensorValue, String sensorMac, String readingDate) {
        this.sensorValue = sensorValue;
        this.sensorMac = sensorMac;
        this.readingDate = readingDate;
    }

    public int getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(int sensorValue) {
        this.sensorValue = sensorValue;
    }

    public String getSensorMac() {
        return sensorMac;
    }

    public void setSensorMac(String sensorMac) {
        this.sensorMac = sensorMac;
    }

    public String getReadingDate() {
        return readingDate;
    }

    public void setReadingDate(String readingDate) {
        this.readingDate = readingDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<Disposal> getDisposals() {
        return disposals;
    }

    public void setDisposals(List<Disposal> disposals) {
        this.disposals = disposals;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
