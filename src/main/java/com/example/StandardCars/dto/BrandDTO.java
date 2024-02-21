package com.example.StandardCars.dto;


import com.example.StandardCars.model.Brand;
import com.example.StandardCars.model.Model;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

public class BrandDTO extends RepresentationModel<BrandDTO> {

    private long id;
    private String name;
    private String country;
    private List<Model> models;

    public BrandDTO(long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.models = new ArrayList<>();
    }

    public BrandDTO() {
        this.id = 0;
        this.name = "";
        this.country = "";
        this.models = new ArrayList<>();
    }

    public BrandDTO(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
        this.country = brand.getCountry();
        this.models = new ArrayList<>();
    }

    public static BrandDTO toBrandDTO(Brand brand){
        return new BrandDTO(brand.getId(), brand.getName(), brand.getCountry());
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

    public List<Model> getModels(){ return models; }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setId(long id) {
        this.id = id;
    }
}
