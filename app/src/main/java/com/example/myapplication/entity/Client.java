package com.example.myapplication.entity;

public class Client {
    private String login;
    private String password;
    private String name;
    private String surname;
    private String address;

    public Client(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Client(String login, String password, String name, String surname, String address) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.address = address;
    }

    // Gettery i settery
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}