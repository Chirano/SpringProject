package com.example.StandardCars.controller;
import com.example.StandardCars.dto.VehicleDTO;
import com.example.StandardCars.model.Vehicle;
import com.example.StandardCars.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class VehicleController {

    @Autowired
    private VehicleService service;


    @GetMapping(value = "/vehicles", produces = "application/json")
    public ResponseEntity<CollectionModel<VehicleDTO>> getVehicles() {
        List<Vehicle> vehicles = service.getVehicles();
        if (vehicles != null){
        List<VehicleDTO> vehiclesDTO = new ArrayList<>();

        for(Vehicle vehicle : vehicles){
            VehicleDTO vehicleDTO = new VehicleDTO(vehicle.getVIN(), vehicle.getModelId(), vehicle.getSellerId(), vehicle.getReleaseYear(),
                    vehicle.getPrice(), vehicle.getFuel(), vehicle.getKilometers(), vehicle. getColor(),
                    vehicle.getGear());
            vehicleDTO.add(linkTo(methodOn(VehicleController.class).getVehicle(vehicleDTO.getVIN())).withSelfRel());
            vehiclesDTO.add(vehicleDTO);
        }
        Link link = linkTo(methodOn(VehicleController.class).getVehicles()).withSelfRel();
        CollectionModel<VehicleDTO> resp = CollectionModel.of(vehiclesDTO, link);

        return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/vehicle/{VIN}")
    public ResponseEntity<VehicleDTO> getVehicle(@PathVariable("VIN") String VIN) {
        Vehicle vehicle = service.getVehicleByVIN(VIN);

        if(vehicle == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        VehicleDTO vehicleDTO = new VehicleDTO(vehicle.getVIN(), vehicle.getModelId(), vehicle.getSellerId(), vehicle.getReleaseYear(),
                vehicle.getPrice(), vehicle.getFuel(), vehicle.getKilometers(), vehicle. getColor(),
                vehicle.getGear());

        return new ResponseEntity<>(vehicleDTO, HttpStatus.OK);
    }


    @PostMapping(value = "/vehicle", consumes = "application/json", produces = "application/json")
    public ResponseEntity<VehicleDTO> addVehicle(@RequestBody VehicleDTO vehicleDTO) {
        Vehicle vehicle = service.addVehicle(vehicleDTO);

        if(vehicle == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        VehicleDTO veDTO = new VehicleDTO(vehicle.getVIN(), vehicle.getModelId(), vehicle.getSellerId(), vehicle.getReleaseYear(),
                vehicle.getPrice(), vehicle.getFuel(), vehicle.getKilometers(), vehicle. getColor(),
                vehicle.getGear());
        veDTO.add(linkTo(methodOn(VehicleController.class).getVehicles()).withRel("all_vehicles"));
        veDTO.add(linkTo(methodOn(VehicleController.class).getVehicle(vehicleDTO.getVIN())).withRel("vehicle_by_VIN"));
        veDTO.add(linkTo(methodOn(VehicleController.class).updateVehicle(vehicleDTO.getVIN(), veDTO)).withRel("update"));
        veDTO.add(linkTo(methodOn(VehicleController.class).deleteVehicle(vehicleDTO.getVIN())).withRel("delete"));

        return new ResponseEntity<>(veDTO, HttpStatus.CREATED);
    }


    @PutMapping(value ="/vehicle/{VIN}", consumes = "application/json", produces = "application/json")
    ResponseEntity<VehicleDTO> updateVehicle(@PathVariable("VIN") String VIN, @RequestBody VehicleDTO vehicleDTO) {
        Vehicle vehicle = service.updateVehicle(VIN, vehicleDTO);
        if(vehicle == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        VehicleDTO upVehicleDTO = new VehicleDTO(vehicle.getVIN(), vehicle.getModelId(), vehicle.getSellerId(), vehicle.getReleaseYear(),
                vehicle.getPrice(), vehicle.getFuel(), vehicle.getKilometers(), vehicle. getColor(),
                vehicle.getGear());

        return new ResponseEntity<>(upVehicleDTO, HttpStatus.OK);
    }


    @DeleteMapping(value = "/vehicle/{VIN}", produces =  "application/json")
    ResponseEntity<VehicleDTO> deleteVehicle(@PathVariable("VIN") String VIN) {
        Vehicle vehicle = service.deleteVehicleByVIN(VIN);

        if(vehicle == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        VehicleDTO vehicleDTO = new VehicleDTO(vehicle.getVIN(), vehicle.getModelId(), vehicle.getSellerId(), vehicle.getReleaseYear(),
                vehicle.getPrice(), vehicle.getFuel(), vehicle.getKilometers(), vehicle. getColor(),
                vehicle.getGear());

        return new ResponseEntity<>(vehicleDTO, HttpStatus.OK);
    }

}