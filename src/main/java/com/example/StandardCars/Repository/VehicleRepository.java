package com.example.StandardCars.Repository;

import com.example.StandardCars.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    List<Vehicle> findVehicleByModelId(@Param("modelId") long modelId);
}