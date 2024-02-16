package com.example.StandardCars.model;

import com.example.StandardCars.dto.ModelDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    private String name;
    @ManyToOne
    private Brand brand;
    @OneToMany
    private List<Vehicle> vehicles;

    public Model(long id, String name, Brand brand) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.vehicles = new ArrayList<>();
    }

    public Model() {
        this.id = 0;
        this.name = "";
        this.brand = new Brand();
        this.vehicles = new ArrayList<>();

    }

    public Model(ModelDTO modelDTO){
        this.id = modelDTO.getId();
        this.name = modelDTO.getName();
        this.brand = new Brand(modelDTO.getBrand());
        this.vehicles = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Brand getBrand() {
        return brand;
    }

    public List<Vehicle> getVehicles(){ return vehicles;}

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

}
