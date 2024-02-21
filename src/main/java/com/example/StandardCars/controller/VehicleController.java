package com.example.StandardCars.controller;
import com.example.StandardCars.Enums.Status;
import com.example.StandardCars.dto.BrandDTO;
import com.example.StandardCars.dto.ModelDTO;
import com.example.StandardCars.dto.PurchaseVehicleDTO;
import com.example.StandardCars.dto.VehicleDTO;
import com.example.StandardCars.model.Brand;
import com.example.StandardCars.model.Model;
import com.example.StandardCars.model.Seller;
import com.example.StandardCars.model.Vehicle;
import com.example.StandardCars.services.BrandService;
import com.example.StandardCars.services.ModelService;
import com.example.StandardCars.services.SellerService;
import com.example.StandardCars.services.VehicleService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTML;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private SellerService sellerService;


    @GetMapping(value = "/vehicles", produces = "application/json")
    public ResponseEntity<CollectionModel<VehicleDTO>> getVehicles(@RequestParam Optional<Integer> page,
                                                                    @RequestParam Optional<Integer> size) {

        int _page = page.orElse(0);
        int _size = size.orElse(10);
        Page<VehicleDTO> vehicles = this.service.getVehicles(_page, _size);

        if(vehicles == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        vehicles = vehicles.map((VehicleDTO v)-> v.add(linkTo(methodOn(VehicleController.class).getVehicle(v.getVIN())).withSelfRel()));

        Link link = linkTo(methodOn(VehicleController.class).getVehicles(Optional.of(_page), Optional.of( _size))).withSelfRel();
        List<Link> links = new ArrayList<>();
        links.add(link);

        if(!vehicles.isLast()) {
            Link _link = linkTo(methodOn(VehicleController.class).getVehicles(Optional.of(_page + 1), Optional.of(_size))).withRel("next");
            links.add(_link);
        }
        if(!vehicles.isFirst()) {
            Link _link = linkTo(methodOn(VehicleController.class).getVehicles(Optional.of(_page - 1), Optional.of(_size))).withRel("previous");
            links.add(_link);
        }
        return new ResponseEntity<>(CollectionModel.of(vehicles, links), HttpStatus.OK);
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
        Model model = modelService.getModelByName(vehicleDTO.getModel());

        if(model == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(vehicleDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Vehicle vehicle = service.addVehicle(vehicleDTO);

        VehicleDTO veDTO = new VehicleDTO(vehicle);

        veDTO.add(linkTo(methodOn(VehicleController.class).getVehicles(Optional.of(1),Optional.of(10))).withRel("all_vehicles"));
        veDTO.add(linkTo(methodOn(VehicleController.class).getVehicle(vehicleDTO.getVIN())).withRel("vehicle_by_id"));
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
        upVehicleDTO.add(linkTo(methodOn(VehicleController.class).updateVehicle(vehicleDTO.getVIN(), upVehicleDTO)).withRel("update"));

        return new ResponseEntity<>(upVehicleDTO, HttpStatus.OK);
    }


    @DeleteMapping(value = "/vehicle/{VIN}", produces =  "application/json")
    ResponseEntity<VehicleDTO> deleteVehicle(@PathVariable("VIN") String VIN) {
        if(service.getVehicleByVIN(VIN) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Vehicle vehicle = service.deleteVehicleByVIN(VIN);

        VehicleDTO vehicleDTO = new VehicleDTO(vehicle);
        vehicleDTO.add(linkTo(methodOn(VehicleController.class).deleteVehicle(vehicleDTO.getVIN())).withRel("delete"));

        return new ResponseEntity<>(vehicleDTO, HttpStatus.OK);
    }


    @PostMapping(value = "/brand", consumes = "application/json")
    ResponseEntity<BrandDTO> addBrand(@RequestBody BrandDTO brandDTO){
        if(brandDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Brand brand = brandService.addBrand(brandDTO);

        BrandDTO brandDTO1 = new BrandDTO(brand);

        brandDTO1.add(linkTo(methodOn(VehicleController.class).getBrands(Optional.of(1),Optional.of(10),Optional.of("name"))).withRel("see_all_brands"));
        brandDTO1.add(linkTo(methodOn(VehicleController.class).getBrand(brandDTO1.getId())).withRel("brand_by_id"));
        brandDTO1.add(linkTo(methodOn(VehicleController.class).updateBrand(brandDTO1.getId(), brandDTO)).withRel("update"));
        brandDTO1.add(linkTo(methodOn(VehicleController.class).deleteBrand(brandDTO1.getId())).withRel("delete"));


        return new ResponseEntity<>(brandDTO1, HttpStatus.CREATED);
    }


    @GetMapping(value = "/brands", produces = "application/json")
    ResponseEntity<CollectionModel<BrandDTO>> getBrands(@RequestParam(name = "page") Optional<Integer> page,
                                                        @RequestParam(name="size") Optional<Integer> size,
                                                        @RequestParam(name="sort")  Optional<String> sort){
        int _page =page.orElse(0);
        int _size =size.orElse(10);
        String _sort = sort.orElse("name");

        Page<BrandDTO> brands = brandService.getBrands(_page, _size, _sort);
        if(brands == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }


        brands = brands.map((BrandDTO b) -> b.add(linkTo(methodOn(VehicleController.class).getBrand(b.getId())).withSelfRel()));

        Link link = linkTo(methodOn(VehicleController.class).getVehicles(Optional.of(1),Optional.of(10))).withSelfRel();
        List<Link> links = new ArrayList<>();
        links.add(link);

        if(!brands.isLast()) {
            Link _link = linkTo(methodOn(VehicleController.class).getBrands(Optional.of(_page + 1), Optional.of(_size),Optional.of(_sort))).withRel("next");
            links.add(_link);
        }
        if(!brands.isFirst()) {
            Link _link = linkTo(methodOn(VehicleController.class).getBrands(Optional.of(_page - 1), Optional.of(_size),Optional.of(_sort))).withRel("previous");
            links.add(_link);
        }
        return new ResponseEntity<>(CollectionModel.of(brands, links), HttpStatus.OK);
    }

    @GetMapping(value = "brand/{id}", produces = "application/json")
    ResponseEntity<BrandDTO> getBrand(@PathVariable("id") Long id){
        if(id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Brand brand = brandService.getBrand(id);

        BrandDTO brandDTO = new BrandDTO(brand);
        brandDTO.add(linkTo(methodOn(VehicleController.class).getBrand(brandDTO.getId())).withSelfRel());

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
        updatedBrand.add(linkTo(methodOn(VehicleController.class).updateBrand(updatedBrand.getId(), brandDTO)).withRel("update"));

        return new ResponseEntity<>(updatedBrand, HttpStatus.OK);
    }


    @DeleteMapping(value = "/brand/{id}", produces =  "application/json")
    ResponseEntity<BrandDTO> deleteBrand(@PathVariable("id") Long id) {
        if(brandService.getBrand(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Brand brand = brandService.deleteBrand(id);

        BrandDTO brandDTO = new BrandDTO(brand);
        brandDTO.add(linkTo(methodOn(VehicleController.class).deleteBrand(brandDTO.getId())).withRel("delete"));


        return new ResponseEntity<>(brandDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/model", consumes = "application/json")
    ResponseEntity<ModelDTO> addModel(@RequestBody ModelDTO modelDTO){
        Brand brand = brandService.getBrandByName(modelDTO.getBrand());
        if(brand == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            ///criar exception para especificar o que nao esta correto
        }

        if(modelDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Model model = modelService.addModel(modelDTO);

        ModelDTO modelDTO1 = new ModelDTO(model);

        modelDTO1.add(linkTo(methodOn(VehicleController.class).getModels(Optional.of(0), Optional.of(10),Optional.of("name"))).withRel("see_all_models"));
        modelDTO1.add(linkTo(methodOn(VehicleController.class).getModel(modelDTO1.getId())).withRel("model_by_id"));
        modelDTO1.add(linkTo(methodOn(VehicleController.class).updateModel(modelDTO1.getId(), modelDTO1)).withRel("update"));
        modelDTO1.add(linkTo(methodOn(VehicleController.class).deleteModel(modelDTO1.getId())).withRel("delete"));

        return new ResponseEntity<>(modelDTO1, HttpStatus.CREATED);
    }

    @GetMapping(value = "/models", produces = "application/json")
    ResponseEntity<CollectionModel<ModelDTO>> getModels(@RequestParam(name = "page") Optional<Integer> page,
                                                        @RequestParam(name="size") Optional<Integer> size,
                                                        @RequestParam(name="sort")  Optional<String> sort){

        int _page=page.orElse(0);
        int _size=size.orElse(10);
        String _sort=sort.orElse("name");

        Page<ModelDTO> models = this.modelService.getModels(_page, _size, _sort);
        if(models == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        models = models.map((ModelDTO m)-> m.add(linkTo(methodOn(VehicleController.class).getModel(m.getId())).withSelfRel()));
        Link link = linkTo(methodOn(VehicleController.class).getModels(Optional.of(_page),Optional.of(_size),Optional.of(_sort))).withSelfRel();
        List<Link> links = new ArrayList<>();
        links.add(link);
        if(!models.isLast()) {
            Link _link = linkTo(methodOn(VehicleController.class).getModels(Optional.of(_page + 1),
                                                            Optional.of(_size),Optional.of(_sort))).withRel("next");
            links.add(_link);
        }
        if(!models.isFirst()) {
            Link _link = linkTo(methodOn(VehicleController.class).getModels(Optional.of(_page - 1),
                                                            Optional.of(_size),Optional.of(_sort))).withRel("previous");
            links.add(_link);
        }
        return new ResponseEntity<>(CollectionModel.of(models, links), HttpStatus.OK);
    }

    @GetMapping(value = "model/{id}", produces = "application/json")
    ResponseEntity<ModelDTO> getModel(@PathVariable("id") Long id){
        if(id == null)
        { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }

        Model model = modelService.getModel(id);

        ModelDTO modelDTO = new ModelDTO(model);
        modelDTO.add(linkTo(methodOn(VehicleController.class).getModel(modelDTO.getId())).withRel("model_by_id"));

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
        modelDTO.add(linkTo(methodOn(VehicleController.class).updateModel(modelDTO.getId(), modelDTO)).withRel("update"));


        return new ResponseEntity<>(updatedModel, HttpStatus.OK);
    }


    @DeleteMapping(value = "/model/{id}", produces =  "application/json")
    ResponseEntity<ModelDTO> deleteModel(@PathVariable("id") Long id) {
        if(modelService.getModel(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Model model = modelService.deleteModel(id);

        ModelDTO modelDTO = new ModelDTO(model);
        modelDTO.add(linkTo(methodOn(VehicleController.class).deleteModel(modelDTO.getId())).withRel("delete"));


        return new ResponseEntity<>(modelDTO, HttpStatus.OK);
    }

    @PutMapping(value = "/vehicle/{VIN}/status/{status}", produces = "application/json")
    ResponseEntity<VehicleDTO> updateVehicleStatus(@PathVariable("VIN") String VIN,
                                                   @RequestBody String status){
       Vehicle vehicle = service.getVehicleByVIN(VIN);

       if(vehicle == null){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
       if(status == null){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }

       Vehicle upVehicle = service.updateVehicleStatus(VIN, status);

       VehicleDTO vehicleDTO = new VehicleDTO(upVehicle);

       return new ResponseEntity<>(vehicleDTO, HttpStatus.OK);
    }


    @PatchMapping(value = "vehicle/{VIN}/buyer/{id}", produces = "application/json")
    ResponseEntity<VehicleDTO> updateVehicleBuyer(@PathVariable("VIN") String VIN,
                                                    @PathVariable String id)
    {
        Vehicle vehicle = service.getVehicleByVIN(VIN);

        if(vehicle == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Vehicle upVehicle = service.updateVehicleBuyer(VIN, id);
        VehicleDTO vehicleDTO = new VehicleDTO(upVehicle);

        return new ResponseEntity<>(vehicleDTO, HttpStatus.OK);
    }


    @PutMapping(value = "vehicle/{VIN}/purchase", produces = "application/json")
    ResponseEntity<VehicleDTO> buyVehicle(@PathVariable("VIN") String VIN,
                                          @RequestBody PurchaseVehicleDTO purDTO){
        Vehicle vehicle = service.getVehicleByVIN(VIN);
        if(vehicle == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(vehicle.getStatus() != Status.Available){
             return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Vehicle upVehicle = service.buyVehicle(VIN, purDTO);
        VehicleDTO vehicleDTO = new VehicleDTO(upVehicle);

        return new ResponseEntity<>(vehicleDTO, HttpStatus.OK);
    }


    @GetMapping(value = "vehicles/model/{model}", produces = "application/json")
    ResponseEntity<CollectionModel<VehicleDTO>> getVehicleByModel(@PathVariable("model") String modelName,
                                                       @RequestParam("page") Optional<Integer> page,
                                                       @RequestParam("size") Optional<Integer> size){
        int _page =page.orElse(0);
        int _size =size.orElse(10);

        Pageable pageable = PageRequest.of(_page, _size);

        Model model = modelService.getModelByName(modelName);
        if(model == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Page<VehicleDTO> vehicles = service.getVehicleByModel(model, pageable);
        if (vehicles == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        vehicles = vehicles.map((VehicleDTO v) -> v.add(linkTo(methodOn(VehicleController.class).getVehicle(v.getVIN())).withSelfRel()));

        Link link = linkTo(methodOn(VehicleController.class).getVehicleByModel(model.getName(), Optional.of(_page),
                                                                    Optional.of(_size))).withSelfRel();
        List<Link> links = new ArrayList<>();
        links.add(link);
        if(!vehicles.isLast()) {
            Link _link = linkTo(methodOn(VehicleController.class).getVehicleByModel(model.getName(), Optional.of(_page + 1), Optional.of(_size))).withRel("next");
            links.add(_link);
        }
        if(!vehicles.isFirst()) {
            Link _link = linkTo(methodOn(VehicleController.class).getVehicleByModel(model.getName(), Optional.of(_page - 1), Optional.of(_size))).withRel("previous");
            links.add(_link);
        }
        return new ResponseEntity<>(CollectionModel.of(vehicles, links), HttpStatus.OK);
    }


    @GetMapping(value = "vehicles/seller/{id}", produces = "application/json")
    ResponseEntity<CollectionModel<VehicleDTO>> getVehicleBySeller(@PathVariable("id") Long id,
                                                        @RequestParam("page") Optional<Integer> page,
                                                        @RequestParam("size") Optional<Integer> size){
        int _page=page.orElse(0);
        int _size=size.orElse(10);

        Pageable pageable = PageRequest.of(_page, _size);
        Seller seller = sellerService.getSellerById(id);
        if(seller == null){
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        Page<VehicleDTO> vehicles = service.getVehicleBySeller(seller, pageable);
        if (vehicles == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        vehicles = vehicles.map((VehicleDTO v) -> v.add(linkTo(methodOn(VehicleController.class).getVehicle(v.getVIN())).withSelfRel()));

        Link link = linkTo(methodOn(VehicleController.class).getVehicleBySeller(seller.getId(), Optional.of( _page),
                                                            Optional.of(_size))).withSelfRel();

        List<Link> links = new ArrayList<>();
        links.add(link);
        if(!vehicles.isLast()) {
            Link _link = linkTo(methodOn(VehicleController.class).getVehicleBySeller(seller.getId(), Optional.of(_page + 1),
                                                Optional.of(_size))).withRel("next");
            links.add(_link);
        }
        if(!vehicles.isFirst()) {
            Link _link = linkTo(methodOn(VehicleController.class).getVehicleBySeller(seller.getId(), Optional.of(_page - 1),
                                                        Optional.of(_size))).withRel("previous");
            links.add(_link);
        }

        return new ResponseEntity<>(CollectionModel.of(vehicles, links), HttpStatus.OK);
    }

    @GetMapping(value = "vehicles/sold", produces = "application/json")
    ResponseEntity<CollectionModel<VehicleDTO>> getSoldVehicles(@RequestParam("page") Optional<Integer> page,
                                                                @RequestParam("size") Optional<Integer> size){
        int _page =page.orElse(0);
        int _size =size.orElse(10);

        Pageable pageable = PageRequest.of(_page, _size);

        Page<VehicleDTO> vehicles = service.getVehiclesSold(pageable);

        if(vehicles == null){
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        vehicles = vehicles.map((VehicleDTO v) -> v.add(linkTo(methodOn(VehicleController.class).getVehicle(v.getVIN())).withSelfRel()));

        Link link = linkTo(methodOn(VehicleController.class).getSoldVehicles(Optional.of( _page),
                Optional.of(_size))).withSelfRel();

        List<Link> links = new ArrayList<>();
        links.add(link);
        if(!vehicles.isLast()) {
            Link _link = linkTo(methodOn(VehicleController.class).getSoldVehicles(Optional.of(_page + 1),
                    Optional.of(_size))).withRel("next");
            links.add(_link);
        }
        if(!vehicles.isFirst()) {
            Link _link = linkTo(methodOn(VehicleController.class).getSoldVehicles(Optional.of(_page - 1),
                    Optional.of(_size))).withRel("previous");
            links.add(_link);
        }

        return new ResponseEntity<>(CollectionModel.of(vehicles, links), HttpStatus.OK);
    }

    @GetMapping(value = "vehicles/status/{status}", produces = "application/json")
    ResponseEntity<CollectionModel<VehicleDTO>> getVehicleByStatus(@PathVariable Status status,
                                                        @RequestParam("page") Optional<Integer> page,
                                                        @RequestParam("size") Optional<Integer> size ){
        int _page =page.orElse(0);
        int _size =size.orElse(10);

        if(status == null){
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Pageable pageable = PageRequest.of(_page, _size);

        Page<VehicleDTO> vehicles = service.getVehicleByStatus(status, pageable);

        if(vehicles == null){
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        vehicles = vehicles.map((VehicleDTO v) -> v.add(linkTo(methodOn(VehicleController.class).getVehicle(v.getVIN())).withSelfRel()));


        Link link = linkTo(methodOn(VehicleController.class).getVehicleByStatus(status, Optional.of(_page),
                                                                    Optional.of(_size))).withSelfRel();

        List<Link> links = new ArrayList<>();
        links.add(link);
        if(!vehicles.isLast()) {
            Link _link = linkTo(methodOn(VehicleController.class).getSoldVehicles(Optional.of(_page + 1),
                    Optional.of(_size))).withRel("next");
            links.add(_link);
        }
        if(!vehicles.isFirst()) {
            Link _link = linkTo(methodOn(VehicleController.class).getSoldVehicles(Optional.of(_page - 1),
                    Optional.of(_size))).withRel("previous");
            links.add(_link);
        }

        return new ResponseEntity<>(CollectionModel.of(vehicles, links), HttpStatus.OK);

    }

    @GetMapping(value = "vehicles/buyer/{id}", produces = "application/json")
    ResponseEntity<CollectionModel<VehicleDTO>> getVehicleByBuyer(@PathVariable("id") String id,
                                                        @RequestParam("page") Optional<Integer> page,
                                                        @RequestParam("size") Optional<Integer> size ){
            int _page =page.orElse(0);
            int _size =size.orElse(10);

            if(id == null){
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Pageable pageable = PageRequest.of(_page, _size);

            Page<VehicleDTO> vehicles = service.getVehicleByBuyer(id, pageable);

            if(vehicles == null){
                new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            vehicles = vehicles.map((VehicleDTO v) -> v.add(linkTo(methodOn(VehicleController.class).getVehicle(v.getVIN())).withSelfRel()));


            Link link = linkTo(methodOn(VehicleController.class).getVehicleByBuyer(id, Optional.of(_page),
                                                                    Optional.of(_size))).withSelfRel();

            List<Link> links = new ArrayList<>();
            links.add(link);
            if(!vehicles.isLast()) {
                Link _link = linkTo(methodOn(VehicleController.class).getVehicleByBuyer(id, Optional.of(_page + 1),
                        Optional.of(_size))).withRel("next");
                links.add(_link);
            }
            if(!vehicles.isFirst()) {
                Link _link = linkTo(methodOn(VehicleController.class).getVehicleByBuyer(id, Optional.of(_page - 1),
                        Optional.of(_size))).withRel("previous");
                links.add(_link);
            }

            return new ResponseEntity<>(CollectionModel.of(vehicles, links), HttpStatus.OK);
    }
}
