package com.example.StandardCars.controller;
import com.example.StandardCars.dto.BrandDTO;
import com.example.StandardCars.dto.ModelDTO;
import com.example.StandardCars.dto.VehicleDTO;
import com.example.StandardCars.model.Brand;
import com.example.StandardCars.model.Model;
import com.example.StandardCars.model.Vehicle;
import com.example.StandardCars.services.BrandService;
import com.example.StandardCars.services.ModelService;
import com.example.StandardCars.services.VehicleService;
import org.apache.coyote.Response;
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
            VehicleDTO vehicleDTO = new VehicleDTO(vehicle.getVIN(), vehicle.getReleaseYear(),
                    vehicle.getPrice(), vehicle.getFuel(), vehicle.getKilometers(), vehicle. getColor(),
                    vehicle.getGear(),  vehicle.getStatus(),
                    vehicle.getModel().getName(), vehicle.getSeller().getName());
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

        VehicleDTO vehicleDTO = new VehicleDTO(vehicle.getVIN(), vehicle.getReleaseYear(),
                vehicle.getPrice(), vehicle.getFuel(), vehicle.getKilometers(), vehicle. getColor(),
                vehicle.getGear(),  vehicle.getStatus(),
                vehicle.getModel().getName(), vehicle.getSeller().getName());

        return new ResponseEntity<>(vehicleDTO, HttpStatus.OK);
    }


    @PostMapping(value = "/vehicle", consumes = "application/json", produces = "application/json")
    public ResponseEntity<VehicleDTO> addVehicle(@RequestBody VehicleDTO vehicleDTO) {
        if(vehicleDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Vehicle vehicle = service.addVehicle(vehicleDTO);



        VehicleDTO veDTO = new VehicleDTO(vehicle.getVIN(), vehicle.getReleaseYear(),
                vehicle.getPrice(), vehicle.getFuel(), vehicle.getKilometers(), vehicle. getColor(),
                vehicle.getGear(),  vehicle.getStatus(),
                vehicle.getModel().getName(), vehicle.getSeller().getName());

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

        VehicleDTO upVehicleDTO = new VehicleDTO(vehicle.getVIN(), vehicle.getReleaseYear(),
                vehicle.getPrice(), vehicle.getFuel(), vehicle.getKilometers(), vehicle. getColor(),
                vehicle.getGear(),  vehicle.getStatus(),
                vehicle.getModel().getName(), vehicle.getSeller().getName());

        return new ResponseEntity<>(upVehicleDTO, HttpStatus.OK);
    }


    @DeleteMapping(value = "/vehicle/{VIN}", produces =  "application/json")
    ResponseEntity<VehicleDTO> deleteVehicle(@PathVariable("VIN") String VIN) {
        if(service.getVehicleByVIN(VIN) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Vehicle vehicle = service.deleteVehicleByVIN(VIN);

        VehicleDTO vehicleDTO = new VehicleDTO(vehicle.getVIN(), vehicle.getReleaseYear(),
                vehicle.getPrice(), vehicle.getFuel(), vehicle.getKilometers(), vehicle. getColor(),
                vehicle.getGear(),  vehicle.getStatus(),
                vehicle.getModel().getName(), vehicle.getSeller().getName());

        return new ResponseEntity<>(vehicleDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/brand", consumes = "application/json")
    ResponseEntity<BrandDTO> addBrand(@RequestBody BrandDTO brandDTO){
        if(brandDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Brand brand = brandService.addBrand(brandDTO);

        BrandDTO brandDTO1 = new BrandDTO(brand.getId(), brand.getName(), brandDTO.getCountry());

        return new ResponseEntity<>(brandDTO1, HttpStatus.CREATED);
    }

    @GetMapping(value = "/brands", produces = "application/json")
    ResponseEntity<CollectionModel<BrandDTO>> getBrands(){
        List<Brand> brands = brandService.getBrands();
        if (brands != null){
            List<BrandDTO> brandsDTO = new ArrayList<>();

            for(Brand brand : brands){
                BrandDTO brandDTO = new BrandDTO(brand.getId(), brand.getName(), brand.getCountry());
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

        BrandDTO brandDTO = new BrandDTO(brand.getId(), brand.getName(), brand.getCountry());

        return new ResponseEntity<>(brandDTO, HttpStatus.OK);
    }


    @PostMapping(value = "/model", consumes = "application/json")
    ResponseEntity<ModelDTO> addModel(@RequestBody ModelDTO modelDTO){
        if(modelDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Model model = modelService.addModel(modelDTO);

        ModelDTO modelDTO1 = new ModelDTO(model.getId(), model.getName(), model.getBrand().getName());

        return new ResponseEntity<>(modelDTO1, HttpStatus.CREATED);
    }

    @GetMapping(value = "/models", produces = "application/json")
    ResponseEntity<CollectionModel<ModelDTO>> getModels(){
        List<Model> models = modelService.getModels();

        if (models != null){
            List<ModelDTO> modelDTOS = new ArrayList<>();

            for(Model model : models){
                ModelDTO modelDTO = new ModelDTO(model.getId(), model.getName(), model.getBrand().getName());
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
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Model model = modelService.getModel(id);

        ModelDTO modelDTO = new ModelDTO(model.getId(), model.getName(), model.getBrand().getName());

        return new ResponseEntity<>(modelDTO, HttpStatus.OK);
    }

}
