package com.example.StandardCars.model;
import com.example.StandardCars.dto.BrandDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
        // this.models = new ArrayList<>();
    }

    public Brand() {
        this.id = 0;
        this.name = "";
        this.country = "";
       // this.models = new ArrayList<>();
    }

    public Brand(BrandDTO brandDTO){
        this.id = brandDTO.getId();
        this.name = brandDTO.getName();
        this.country = brandDTO.getCountry();
      //  this.models = new ArrayList<>();
    }

    public Brand(String name){
        this.name = name;
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
   // public List<Model> getModels(){return models;}

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
