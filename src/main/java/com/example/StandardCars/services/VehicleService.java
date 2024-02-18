package com.example.StandardCars.services;

import com.example.StandardCars.Repository.ModelRepository;
import com.example.StandardCars.Repository.SellerRepository;
import com.example.StandardCars.Repository.VehicleRepository;
import com.example.StandardCars.dto.VehicleDTO;
import com.example.StandardCars.model.Model;
import com.example.StandardCars.model.Seller;
import com.example.StandardCars.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    SellerRepository sellerRepository;

    public Vehicle getVehicleByVIN(String VIN){
        return this.vehicleRepository.findById(VIN).get();
    }

    public List<Vehicle> getVehicles() {
        return this.vehicleRepository.findAll();
    }

    public List<Vehicle> getVehicleByModel(Model model){
        return this.vehicleRepository.findVehicleByModel(model);
    }

    public Vehicle addVehicle(VehicleDTO vehicleDTO){
         Model model = getModel(vehicleDTO.getModel());
         Seller seller = getSeller(vehicleDTO.getSeller());

        return vehicleRepository.save(new Vehicle(vehicleDTO.getVIN(), vehicleDTO.getReleaseYear(),
                vehicleDTO.getPrice(), vehicleDTO.getFuel(), vehicleDTO.getKilometers(), vehicleDTO. getColor(),
                vehicleDTO.getGear(), vehicleDTO.getStatus(),  model, seller));
    }

    public Vehicle updateVehicle(String VIN, VehicleDTO vehicleDTO){
        Vehicle vehicle = vehicleRepository.findById(VIN).get();

        if(vehicle == null){
            return null;}

        vehicleDTO.setVIN(VIN);

        Model model = getModel(vehicleDTO.getModel());
        Seller seller = getSeller(vehicleDTO.getSeller());

        Vehicle ve = vehicleRepository.save(new Vehicle(vehicleDTO.getVIN(), vehicleDTO.getReleaseYear(),
                vehicleDTO.getPrice(), vehicleDTO.getFuel(), vehicleDTO.getKilometers(), vehicleDTO. getColor(),
                vehicleDTO.getGear(), vehicleDTO.getStatus(),  model, seller));
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

    public Model getModel(String modelName){
        return modelRepository.findModelByName(modelName);
    }

    public Seller getSeller(String sellerName){
        return sellerRepository.findSellerByName(sellerName);
    }
}
