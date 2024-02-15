package com.example.StandardCars.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;

public class Seller {
    @Id
    private long Id;

    private String name;

    private String email;

    private String phoneNumber;

    public Seller(long id, String name, String email, String phoneNumber) {
        Id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Seller() {
        Id = 0;
        this.name = " ";
        this.email = " ";
        this.phoneNumber = " ";
    }

    public long getId() {
        return Id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
