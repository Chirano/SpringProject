package com.example.StandardCars.dto;

import com.example.StandardCars.Enums.Status;
import com.example.StandardCars.model.Vehicle;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
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

    public static VehicleDTO toVehicleDTO(Vehicle v){
            return new VehicleDTO(v.getVIN(), v.getReleaseYear(), v.getPrice(), v.getFuel(), v.getKilometers(),
                            v.getColor(), v.getGear(), v.getStatus(), v.getBuyerId(), v.getTransactionId(),
                            v.getModel().getName(), v.getSeller().getName());
    }

}
