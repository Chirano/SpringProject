package com.example.StandardCars.dto;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.hateoas.RepresentationModel;

public class BrandDTO extends RepresentationModel<BrandDTO> {

    private long id;
    private String name;
    private String country;

    public BrandDTO(long id, String name, String country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    public BrandDTO() {
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

    public void setId(long id) {
        this.id = id;
    }
}
