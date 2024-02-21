package com.example.StandardCars.Repository;

import com.example.StandardCars.Enums.Status;
import com.example.StandardCars.model.Model;
import com.example.StandardCars.model.Seller;
import com.example.StandardCars.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {
    Page<Vehicle> findVehicleByModel(Model model, Pageable page);

    Page<Vehicle> findVehicleBySeller(Seller seller, Pageable page);

    Page<Vehicle> findVehicleByStatus(Status status, Pageable page);

    Page<Vehicle> findVehicleByBuyerId(String id, Pageable page);

}
