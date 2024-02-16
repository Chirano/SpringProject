package com.example.StandardCars.services;

import com.example.StandardCars.Repository.VehicleRepository;
import com.example.StandardCars.dto.VehicleDTO;
import com.example.StandardCars.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    public Vehicle getVehicleByVIN(String VIN){
        return this.vehicleRepository.findById(VIN).get();
    }

    public List<Vehicle> getVehicles() {
        return this.vehicleRepository.findAll();
    }

    public List<Vehicle> getVehicleByModelId(int modelId){
        return this.vehicleRepository.findVehicleByModelId(modelId);
    }

    public Vehicle addVehicle(VehicleDTO vehicleDTO){

        Vehicle vehicle = vehicleRepository.save(new Vehicle(vehicleDTO.getVIN(), vehicleDTO.getModelId(), vehicleDTO.getSellerId(), vehicleDTO.getReleaseYear(),
                                vehicleDTO.getPrice(), vehicleDTO.getFuel(), vehicleDTO.getKilometers(), vehicleDTO. getColor(),
                                vehicleDTO.getGear()));
        return vehicle;
    }

    public Vehicle updateVehicle(String VIN, VehicleDTO vehicleDTO){
        Vehicle vehicle = vehicleRepository.findById(VIN).get();
        if(vehicle == null){
            return null;}

        vehicleDTO.setVIN(VIN);
        Vehicle ve =vehicleRepository.save(new Vehicle(vehicleDTO.getVIN(), vehicleDTO.getModelId(), vehicleDTO.getSellerId(), vehicleDTO.getReleaseYear(),
                vehicleDTO.getPrice(), vehicleDTO.getFuel(), vehicleDTO.getKilometers(), vehicleDTO. getColor(),
                vehicleDTO.getGear()));
        return ve;
    }

    public Vehicle deleteVehicleByVIN(String VIN){
        Vehicle vehicle = vehicleRepository.findById(VIN).get();
        if(vehicle == null){
            return null;
        }

        vehicleRepository.deleteById(VIN);
        return vehicle;
    }
}
