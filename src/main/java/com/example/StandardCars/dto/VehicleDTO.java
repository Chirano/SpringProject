package com.example.StandardCars.dto;

import com.example.StandardCars.Enums.Status;
import com.example.StandardCars.model.Vehicle;
import org.springframework.hateoas.RepresentationModel;

public class VehicleDTO extends RepresentationModel<VehicleDTO> {
    private String VIN;
    private Integer releaseYear;
    private double price;
    private String fuel;
    private long kilometers;
    private String color;
    private String gear;
    private Status status;
    private String buyerId;
    private String transactionId;
    private String model;
    private String seller;


    public VehicleDTO(String VIN, Integer releaseYear, double price, String fuel, long kilometers,
                      String color, String gear, Status status, String buyerId, String transactionId,
                      String model, String seller) {
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

    public VehicleDTO() {
        this.VIN = "";
        this.releaseYear = 0;
        this.price = 0;
        this.fuel = "";
        this.kilometers = 0;
        this.color = "";
        this.gear = "";
        this.status = Status.Available;
        this.model = "";
        this.seller = "";
    }

    public VehicleDTO(Vehicle vehicle){
        this.VIN = vehicle.getVIN();
        this.releaseYear = vehicle.getReleaseYear();
        this.price = vehicle.getPrice();
        this.fuel = vehicle.getFuel();
        this.kilometers = vehicle.getKilometers();
        this.color = vehicle.getColor();
        this.gear = vehicle.getGear();
        this.status = vehicle.getStatus();
        this.buyerId = vehicle.getBuyerId();
        this.transactionId = vehicle.getTransactionId();
        this.model = vehicle.getModel().getName();
        this.seller = vehicle.getSeller().getName();
    }

    public String getSeller() {
        return seller;
    }

    public String getVIN() {
        return VIN;
    }

    public String getModel() {
        return model;
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

    public Status getStatus() { return status; }

    public String getBuyerId() {
        return buyerId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public void setModelId(String model) {
        this.model = model;
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

    public void setSeller(String seller) {
        this.seller = seller;
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

    public void setStatus(Status status) {  this.status = status; }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
