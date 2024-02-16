package com.example.StandardCars.model;
import jakarta.persistence.*;

@Entity
public class Vehicle {

    @Id
    private String VIN;
    private Integer releaseYear;
    private double price;
    private String fuel;
    private long kilometers;
    private String color;
    private String gear;
    private long modelId;
    private long sellerId;


    public Vehicle(String VIN, long modelId, long sellerId, Integer releaseYear, double price,
                   String fuel, long kilometers, String color, String gear) {
        this.VIN = VIN;
        this.modelId = modelId;
        this.sellerId = sellerId;
        this.releaseYear = releaseYear;
        this.price = price;
        this.fuel = fuel;
        this.kilometers = kilometers;
        this.color = color;
        this.gear = gear;

    }

    public Vehicle() {
        this.VIN = "";
        this.modelId = 0;
        this.sellerId = 0;
        this.releaseYear = 0;
        this.fuel = "";
        this.kilometers = 0;
        this.color = "";
        this.gear = "";
    }

    public long getSellerId() {
        return sellerId;
    }

    public String getVIN() {
        return VIN;
    }

    public long getModelId() {
        return modelId;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public double getPrice() {
        return price;
    }

    public String getFuel() {
        return fuel;
    }

    public long getKilometers() {
        return kilometers;
    }

    public String getColor() {
        return color;
    }

    public String getGear() {
        return gear;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public void setReleaseYear(int year) {
        this.releaseYear = year;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public void setKilometers(long kilometers) {
        this.kilometers = kilometers;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setGear(String gear) {
        this.gear = gear;
    }
}
