package com.example.StandardCars.services;
import com.example.StandardCars.Enums.Status;
import com.example.StandardCars.Exceptions.RestResponseEntityException;
import com.example.StandardCars.Repository.ModelRepository;
import com.example.StandardCars.Repository.SellerRepository;
import com.example.StandardCars.Repository.VehicleRepository;
import com.example.StandardCars.dto.PurchaseVehicleDTO;
import com.example.StandardCars.dto.VehicleDTO;
import com.example.StandardCars.model.Model;
import com.example.StandardCars.model.Seller;
import com.example.StandardCars.model.Vehicle;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    SellerRepository sellerRepository;

    public Vehicle getVehicleByVIN(String VIN){
        return this.vehicleRepository.findById(VIN).orElseThrow(() -> new NoSuchElementException("Vehicle not found"));
    }

    public List<Vehicle> getVehicles() {
        return this.vehicleRepository.findAll();
    }

    public List<Vehicle> getVehicleByModel(Model model){
        return this.vehicleRepository.findVehicleByModel(model);
    }

    @Transactional
    public Vehicle addVehicle(VehicleDTO vehicleDTO){

         Model model = getModel(vehicleDTO.getModel());
         Seller seller = getSeller(vehicleDTO.getSeller());

        return vehicleRepository.save(new Vehicle(vehicleDTO.getVIN(), vehicleDTO.getReleaseYear(), vehicleDTO.getPrice(),
                                    vehicleDTO.getFuel(), vehicleDTO.getKilometers(),  vehicleDTO.getColor(),
                                    vehicleDTO.getGear(), vehicleDTO.getStatus(), vehicleDTO.getBuyerId(),
                                    vehicleDTO.getTransactionId(), model, seller));
    }

    @Transactional
    public Vehicle updateVehicle(String VIN, VehicleDTO vehicleDTO){
        Vehicle vehicle = vehicleRepository.findById(VIN).get();

        if(vehicle == null){
            return null;}

        vehicleDTO.setVIN(VIN);

        Model model = getModel(vehicleDTO.getModel());
        Seller seller = getSeller(vehicleDTO.getSeller());

        Vehicle ve = vehicleRepository.save(new Vehicle(vehicleDTO.getVIN(), vehicleDTO.getReleaseYear(), vehicleDTO.getPrice(),
                vehicleDTO.getFuel(), vehicleDTO.getKilometers(),  vehicleDTO.getColor(),
                vehicleDTO.getGear(), vehicleDTO.getStatus(), vehicleDTO.getBuyerId(),
                vehicleDTO.getTransactionId(), model, seller));
        return ve;
    }

    public Vehicle deleteVehicleByVIN(String VIN){
        Vehicle vehicle = vehicleRepository.findById(VIN).orElseThrow(() ->new NoSuchElementException("VIN not found"));
        if(vehicle == null){
            return null;
        }

        vehicleRepository.deleteById(VIN);
        return vehicle;
    }

    public Vehicle updateVehicleStatus(String VIN, String status){
        Vehicle vehicle = vehicleRepository.findById(VIN).orElseThrow(NoSuchElementException::new);

        Status newStatus = Status.valueOf(status);
        vehicle.setStatus(newStatus);
        Vehicle upVehicle = vehicleRepository.save(vehicle);

        return upVehicle;
    }


    public Vehicle updateVehicleBuyer(String VIN, String buyerId){
        Vehicle vehicle = vehicleRepository.findById(VIN).get();

        vehicle.setBuyerId(buyerId);
        Vehicle upVehicle = vehicleRepository.save(vehicle);

        return upVehicle;
    }

    public Vehicle buyVehicle(String VIN, PurchaseVehicleDTO purchaseDTO){
        Vehicle vehicle = vehicleRepository.findById(VIN).get();

        vehicle.setStatus(Status.Sold);
        vehicle.setBuyerId(purchaseDTO.getBuyerId());
        vehicle.setTransactionId(purchaseDTO.getTransactionId());

        Vehicle upVehicle = vehicleRepository.save(vehicle);

        return upVehicle;
    }

    public Vehicle updateVehicleTransaction(String VIN, String transId){
        Vehicle vehicle = vehicleRepository.findById(VIN).get();

        vehicle.setTransactionId(transId);
        Vehicle upVehicle = vehicleRepository.save(vehicle);

        return upVehicle;
    }


    @Transactional
    public List<Vehicle> getVehicleByModel(String modelName){
        Model model =  modelRepository.findModelByName(modelName);
        if(model == null){
            return null;
        }

        List<Vehicle> vehicles = vehicleRepository.findVehicleByModel(model);

        return vehicles;
    }
    @Transactional
    public List<Vehicle> getVehicleBySeller(long id){
        Seller seller =  sellerRepository.findById(id).orElse(null);

        List<Vehicle> vehicles = vehicleRepository.findVehicleBySeller(seller);

        return vehicles;
    }

    @Transactional
    public List<Vehicle> getVehicleByStatus(Status status){
        List<Vehicle> vehicles = vehicleRepository.findVehicleByStatus(status);

        return vehicles;
    }
    @Transactional
    public List<Vehicle> getVehiclesSold(){
        List<Vehicle> vehicles = vehicleRepository.findVehicleByStatus(Status.Sold);
        return vehicles;
    }

    @Transactional
    public List <Vehicle> getVehicleByBuyer(String id){
        List<Vehicle> vehicles = vehicleRepository.findVehicleByBuyerId(id);

        if(vehicles == null) {return  null; }

        return vehicles;
    }


    public Model getModel(String modelName){
        return modelRepository.findModelByName(modelName);
    }

    public Seller getSeller(String sellerName){
        return sellerRepository.findSellerByName(sellerName);
    }
}
