package com.example.StandardCars.dto;

import org.springframework.hateoas.RepresentationModel;

public class ModelDTO extends RepresentationModel<ModelDTO> {
    public long id;

    private String name;

    private Integer brandId;


    public ModelDTO(long id, String name, int brandId) {
        this.id = id;
        this.name = name;
        this.brandId = brandId;
    }

    public ModelDTO() {
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

    public void setId(long id) {
        this.id = id;
    }
}
