package com.example.StandardCars.model;
import com.example.StandardCars.Enums.Status;
import com.example.StandardCars.dto.VehicleDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Vehicle {

    @Id
    private String VIN;
    private Integer releaseYear;
    private double price;
    private String fuel;
    private long kilometers;
    private String color;
    private String gear;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String buyerId;
    private String transactionId;
    @NotNull
    @ManyToOne
    private Model model;
    @NotNull
    @ManyToOne
    private Seller seller;


    public Vehicle(String VIN, Integer releaseYear, double price, String fuel, long kilometers,
                   String color, String gear, Status status, String buyerId, String transactionId,
                   Model model, Seller seller) {
        this.VIN = VIN;
        this.releaseYear = releaseYear;
        this.price = price;
        this.fuel = fuel;
        this.kilometers = kilometers;
        this.color = color;
        this.gear = gear;
        this.status = status;
        this.buyerId = buyerId;
        this.transactionId = transactionId;
        this.model = model;
        this.seller = seller;
    }

    public Vehicle() {
        this.VIN = "";
        this.releaseYear = 0;
        this.price = 0;
        this.fuel = "";
        this.kilometers = 0;
        this.color = "";
        this.gear = "";
        this.status = Status.Available;
        this.buyerId = "";
        this.transactionId = "";
        this.model = new Model();
        this.seller = new Seller();
    }
}