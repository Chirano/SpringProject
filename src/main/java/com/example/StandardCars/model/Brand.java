package com.example.StandardCars.model;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String country;

    public Brand(long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public Brand() {
        this.id = 0;
        this.name = "";
        this.country = "";
    }

    public long getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public String getCountry() {
        return country;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
