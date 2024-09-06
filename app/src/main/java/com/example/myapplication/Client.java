package com.example.myapplication;

public class Client {
    private String login;
    private String password;

    public Client(String login, String password) {
        this.login = login;
        this.password = password;
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
}