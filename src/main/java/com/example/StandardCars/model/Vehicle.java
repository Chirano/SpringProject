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
    @ManyToOne
    private Model model;
    @ManyToOne
    private Seller seller;


    public Vehicle(String VIN, Model model, Seller seller, Integer releaseYear, double price,
                   String fuel, long kilometers, String color, String gear) {
        this.VIN = VIN;
        this.model = model;
        this.seller = seller;
        this.releaseYear = releaseYear;
        this.price = price;
        this.fuel = fuel;
        this.kilometers = kilometers;
        this.color = color;
        this.gear = gear;

    }

    public Vehicle() {
        this.VIN = "";
        this.model = new Model();
        this.seller = new Seller();
        this.releaseYear = 0;
        this.fuel = "";
        this.kilometers = 0;
        this.color = "";
        this.gear = "";
    }

    public Seller getSeller() {
        return seller;
    }

    public String getVIN() {
        return VIN;
    }

    public Model getModel() {
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

    public void setModelId(Model model) {
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

    public void setSeller(Seller seller) {
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
}
