package com.example.StandardCars.dto;

import com.example.StandardCars.model.Seller;
import com.example.StandardCars.model.Vehicle;
import jakarta.persistence.Id;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

public class SellerDTO extends RepresentationModel<SellerDTO> {

    @Id
    private long Id;

    private String name;

    private String email;

    private String phoneNumber;

    private ArrayList<Vehicle> vehicles;

    public SellerDTO(long id, String name, String email, String phoneNumber) {
        Id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.vehicles = new ArrayList<>();
    }

    public SellerDTO() {
        Id = 0;
        this.name = " ";
        this.email = " ";
        this.phoneNumber = " ";
        this.vehicles = new ArrayList<>();
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

    public List<Vehicle> getVehicles() { return this.vehicles; }

    public void setId(long id) { this.Id = id; }

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
