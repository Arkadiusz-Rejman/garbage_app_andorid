package com.example.myapplication.entity;

import java.time.LocalDate;

public class Disposal {

    private Long id;
    private boolean isDisposalScheduled;
    private String disposalDate;
    private DisposalType disposalType;

    private Sensor sensor;

    public Disposal() {
    }

    public Disposal(boolean isDisposalScheduled, String disposalDate, Sensor sensor, DisposalType disposalType) {
        this.isDisposalScheduled = isDisposalScheduled;
        this.disposalDate = disposalDate;
        this.sensor = sensor;
        this.disposalType = disposalType;
    }


    public Disposal(boolean isDisposalScheduled, String disposalDate, DisposalType disposalType) {
        this.isDisposalScheduled = isDisposalScheduled;
        this.disposalDate = disposalDate;
        this.disposalType = disposalType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isDisposalScheduled() {
        return isDisposalScheduled;
    }

    public void setDisposalScheduled(boolean disposalScheduled) {
        isDisposalScheduled = disposalScheduled;
    }

    public String getDisposalDate() {
        return disposalDate;
    }

    public void setDisposalDate(String disposalDate) {
        this.disposalDate = disposalDate;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public DisposalType getDisposalType() {
        return disposalType;
    }

    public void setDisposalType(DisposalType disposalType) {
        this.disposalType = disposalType;
    }
}
