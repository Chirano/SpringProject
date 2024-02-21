package com.example.StandardCars.model;
import com.example.StandardCars.dto.SellerDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    private String name;


    private String email;


    private String phoneNumber;

    public Seller(long id, String name, String email, String phoneNumber) {
        Id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        //this.vehicles = new ArrayList<>();
    }


    public Seller() {
        this.Id = 0;
        this.name = " ";
        this.email = " ";
        this.phoneNumber = " ";
       // this.vehicles = new ArrayList<>();
    }

    public Seller(SellerDTO sellerDTO){
        this.Id = sellerDTO.getId();
        this.name = sellerDTO.getName();
        this.email = sellerDTO.getEmail();
        this.phoneNumber = sellerDTO.getPhoneNumber();
       // this.vehicles = new ArrayList<>();
    }

    public long getId() {  return Id;   }


    public String getName() {
        return name;
    }


    public String getEmail() {
        return email;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }


   // public List<Vehicle> getVehicles() { return this.vehicles; }


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
