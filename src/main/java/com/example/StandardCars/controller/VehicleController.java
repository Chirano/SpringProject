package com.example.StandardCars.controller;
import com.example.StandardCars.Enums.Status;
import com.example.StandardCars.dto.BrandDTO;
import com.example.StandardCars.dto.ModelDTO;
import com.example.StandardCars.dto.VehicleDTO;
import com.example.StandardCars.model.Brand;
import com.example.StandardCars.model.Model;
import com.example.StandardCars.model.Vehicle;
import com.example.StandardCars.services.BrandService;
import com.example.StandardCars.services.ModelService;
import com.example.StandardCars.services.VehicleService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api")
public class VehicleController {

    @Autowired
    private VehicleService service;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ModelService modelService;


    @GetMapping(value = "/vehicles", produces = "application/json")
    public ResponseEntity<CollectionModel<VehicleDTO>> getVehicles() {
        List<Vehicle> vehicles = service.getVehicles();
        if (vehicles != null){
        List<VehicleDTO> vehiclesDTO = new ArrayList<>();

        for(Vehicle vehicle : vehicles){
            VehicleDTO vehicleDTO = new VehicleDTO(vehicle);
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

        VehicleDTO vehicleDTO = new VehicleDTO(vehicle);

        return new ResponseEntity<>(vehicleDTO, HttpStatus.OK);
    }


    @PostMapping(value = "/vehicle", consumes = "application/json", produces = "application/json")
    public ResponseEntity<VehicleDTO> addVehicle(@RequestBody VehicleDTO vehicleDTO) {
        if(vehicleDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Vehicle vehicle = service.addVehicle(vehicleDTO);

        VehicleDTO veDTO = new VehicleDTO(vehicle);

        veDTO.add(linkTo(methodOn(VehicleController.class).getVehicles()).withRel("all_vehicles"));
        veDTO.add(linkTo(methodOn(VehicleController.class).getVehicle(vehicleDTO.getVIN())).withRel("vehicle_by_VIN"));
        veDTO.add(linkTo(methodOn(VehicleController.class).updateVehicle(vehicleDTO.getVIN(), veDTO)).withRel("update"));
        veDTO.add(linkTo(methodOn(VehicleController.class).deleteVehicle(vehicleDTO.getVIN())).withRel("delete"));

        return new ResponseEntity<>(veDTO, HttpStatus.CREATED);
    }


    @PutMapping(value ="/vehicle/{VIN}", consumes = "application/json", produces = "application/json")
    ResponseEntity<VehicleDTO> updateVehicle(@PathVariable("VIN") String VIN,
                                             @RequestBody VehicleDTO vehicleDTO) {
        if(vehicleDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(service.getVehicleByVIN(VIN) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Vehicle vehicle = service.updateVehicle(VIN, vehicleDTO);

        VehicleDTO upVehicleDTO = new VehicleDTO(vehicle);

        return new ResponseEntity<>(upVehicleDTO, HttpStatus.OK);
    }


    @DeleteMapping(value = "/vehicle/{VIN}", produces =  "application/json")
    ResponseEntity<VehicleDTO> deleteVehicle(@PathVariable("VIN") String VIN) {
        if(service.getVehicleByVIN(VIN) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Vehicle vehicle = service.deleteVehicleByVIN(VIN);

        VehicleDTO vehicleDTO = new VehicleDTO(vehicle);

        return new ResponseEntity<>(vehicleDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/brand", consumes = "application/json")
    ResponseEntity<BrandDTO> addBrand(@RequestBody BrandDTO brandDTO){
        if(brandDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Brand brand = brandService.addBrand(brandDTO);

        BrandDTO brandDTO1 = new BrandDTO(brand);

        brandDTO1.add(linkTo(methodOn(VehicleController.class).getBrands()).withRel("see_all_brands"));
        brandDTO1.add(linkTo(methodOn(VehicleController.class).getBrand(brandDTO1.getId())).withRel("brand_by_VIN"));
        brandDTO1.add(linkTo(methodOn(VehicleController.class).updateBrand(brandDTO1.getId(), brandDTO)).withRel("update"));
        brandDTO1.add(linkTo(methodOn(VehicleController.class).deleteBrand(brandDTO1.getId())).withRel("delete"));


        return new ResponseEntity<>(brandDTO1, HttpStatus.CREATED);
    }

    @GetMapping(value = "/brands", produces = "application/json")
    ResponseEntity<CollectionModel<BrandDTO>> getBrands(){
        List<Brand> brands = brandService.getBrands();
        if (brands != null){
            List<BrandDTO> brandsDTO = new ArrayList<>();

            for(Brand brand : brands){
                BrandDTO brandDTO = new BrandDTO(brand);
                brandDTO.add(linkTo(methodOn(VehicleController.class).getBrand(brandDTO.getId())).withSelfRel());
                brandsDTO.add(brandDTO);
            }

            Link link = linkTo(methodOn(VehicleController.class).getBrands()).withSelfRel();
            CollectionModel<BrandDTO> resp = CollectionModel.of(brandsDTO, link);

            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "brand/{id}", produces = "application/json")
    ResponseEntity<BrandDTO> getBrand(@PathVariable("id") Long id){
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Brand brand = brandService.getBrand(id);

        BrandDTO brandDTO = new BrandDTO(brand);

        return new ResponseEntity<>(brandDTO, HttpStatus.OK);
    }

    @PutMapping(value ="/brand/{id}", consumes = "application/json", produces = "application/json")
    ResponseEntity<BrandDTO> updateBrand(@PathVariable("id") Long id,
                                             @RequestBody BrandDTO brandDTO) {
        if(brandDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(brandService.getBrand(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Brand brand = brandService.updateBrand(id, brandDTO);

        BrandDTO updatedBrand = new BrandDTO(brand);

        return new ResponseEntity<>(updatedBrand, HttpStatus.OK);
    }


    @DeleteMapping(value = "/brand/{id}", produces =  "application/json")
    ResponseEntity<BrandDTO> deleteBrand(@PathVariable("id") Long id) {
        if(brandService.getBrand(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Brand brand = brandService.deleteBrand(id);

        BrandDTO brandDTO = new BrandDTO(brand);

        return new ResponseEntity<>(brandDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/model", consumes = "application/json")
    ResponseEntity<ModelDTO> addModel(@RequestBody ModelDTO modelDTO){
        if(modelDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Model model = modelService.addModel(modelDTO);

        ModelDTO modelDTO1 = new ModelDTO(model);

        return new ResponseEntity<>(modelDTO1, HttpStatus.CREATED);
    }

    @GetMapping(value = "/models", produces = "application/json")
    ResponseEntity<CollectionModel<ModelDTO>> getModels(){
        List<Model> models = modelService.getModels();

        if (models != null){
            List<ModelDTO> modelDTOS = new ArrayList<>();

            for(Model model : models){
                ModelDTO modelDTO = new ModelDTO(model);
                modelDTO.add(linkTo(methodOn(VehicleController.class).getModel(modelDTO.getId())).withSelfRel());
                modelDTOS.add(modelDTO);
            }

            Link link = linkTo(methodOn(VehicleController.class).getModels()).withSelfRel();
            CollectionModel<ModelDTO> resp = CollectionModel.of(modelDTOS, link);

            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "model/{id}", produces = "application/json")
    ResponseEntity<ModelDTO> getModel(@PathVariable("id") Long id){
        if(id == null)
        { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }

        Model model = modelService.getModel(id);

        ModelDTO modelDTO = new ModelDTO(model);

        return new ResponseEntity<>(modelDTO, HttpStatus.OK);
    }

    @PutMapping(value ="/model/{id}", consumes = "application/json", produces = "application/json")
    ResponseEntity<ModelDTO> updateModel(@PathVariable("id") Long id,
                                         @RequestBody ModelDTO modelDTO) {
        if(modelDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(brandService.getBrand(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Model model = modelService.updateModel(id, modelDTO);

        ModelDTO updatedModel = new ModelDTO(model);

        return new ResponseEntity<>(updatedModel, HttpStatus.OK);
    }


    @DeleteMapping(value = "/model/{id}", produces =  "application/json")
    ResponseEntity<ModelDTO> deleteModel(@PathVariable("id") Long id) {
        if(modelService.getModel(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Model model = modelService.deleteModel(id);

        ModelDTO modelDTO = new ModelDTO(model);

        return new ResponseEntity<>(modelDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/vehicle/{VIN}/status/{status}", produces = "application/json")
    ResponseEntity<VehicleDTO> updateVehicleStatus(@PathVariable("VIN") String VIN,
                                                   @PathVariable String status){
       Vehicle vehicle = service.getVehicleByVIN(VIN);

       if(vehicle == null){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }

       if(status == null)
       {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }

       Vehicle upVehicle = service.updateVehicleStatus(VIN, status);

       VehicleDTO vehicleDTO = new VehicleDTO(upVehicle);

       return new ResponseEntity<>(vehicleDTO, HttpStatus.OK);
    }

    @GetMapping(value = "vehicles/{model}", produces = "application/json")
    ResponseEntity<List<VehicleDTO>> getVehicleByModel(@PathVariable("model") String model){
        List<Vehicle> vehicles = service.getVehicleByModel(model);

        if (vehicles == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<VehicleDTO> vehicleDTOS = new ArrayList<>();

        for(Vehicle vehicle : vehicles){
            VehicleDTO vehicleDTO = new VehicleDTO(vehicle);
            vehicleDTOS.add(vehicleDTO);
        }

        return new ResponseEntity<>(vehicleDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "vehicle/seller/{id}", produces = "application/json")
    ResponseEntity<List<VehicleDTO>> getVehicleByModel(@PathVariable("id") Long id){
        List<Vehicle> vehicles = service.getVehicleBySeller(id);

        if (vehicles == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<VehicleDTO> vehicleDTOS = new ArrayList<>();

        for(Vehicle vehicle : vehicles){
            VehicleDTO vehicleDTO = new VehicleDTO(vehicle);
            vehicleDTOS.add(vehicleDTO);
        }

        return new ResponseEntity<>(vehicleDTOS, HttpStatus.OK);
    }

}
