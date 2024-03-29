package com.example.StandardCars.dto;

import com.example.StandardCars.model.Model;
import org.springframework.hateoas.RepresentationModel;

public class ModelDTO extends RepresentationModel<ModelDTO> {
    public long id;

    private String name;

    private String brand;


    public ModelDTO(long id, String name, String brand) {
        this.id = id;
        this.name = name;
        this.brand = brand;
    }

    public ModelDTO() {
        this.id = 0;
        this.name = "";
        this.brand = "";

    }

    public ModelDTO(Model model) {
        this.id = model.getId();
        this.name = model.getName();
        this.brand = model.getBrand().getName();
    }

    public static ModelDTO toModelDTO(Model model){
        return new ModelDTO(model.getId(), model.getName(), model.getBrand().getName());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setId(long id) {
        this.id = id;
    }
}
