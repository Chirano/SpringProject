package com.example.StandardCars.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    private String name;

    private Integer brandId;

    public Model(long id, String name, int brandId) {
        this.id = id;
        this.name = name;
        this.brandId = brandId;
    }

    public Model() {
        this.id = 0;
        this.name = "";
        this.brandId = 0;

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(int brand) {
        this.brandId = brand;
    }

}
