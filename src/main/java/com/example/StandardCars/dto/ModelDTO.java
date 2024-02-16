package com.example.StandardCars.dto;

import com.example.StandardCars.model.Model;
import org.springframework.hateoas.RepresentationModel;

public class ModelDTO extends RepresentationModel<ModelDTO> {
    public long id;

    private String name;

    private BrandDTO brand;


    public ModelDTO(long id, String name, BrandDTO brand) {
        this.id = id;
        this.name = name;
        this.brand = brand;
    }

    public ModelDTO() {
        this.id = 0;
        this.name = "";
        this.brand = new BrandDTO();

    }

    public ModelDTO(Model model) {
        this.id = model.getId();
        this.name = model.getName();
        this.brand = new BrandDTO(model.getBrand());
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BrandDTO getBrand() {
        return brand;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrand(BrandDTO brand) {
        this.brand = brand;
    }

    public void setId(long id) {
        this.id = id;
    }
}
