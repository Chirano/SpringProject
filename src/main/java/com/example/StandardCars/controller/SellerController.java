package com.example.StandardCars.controller;
import com.example.StandardCars.dto.SellerDTO;
import com.example.StandardCars.model.Seller;
import com.example.StandardCars.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class SellerController {

    @Autowired
    SellerService service;

    @GetMapping(value = "/sellers", produces = "application/json")
    public ResponseEntity<CollectionModel<SellerDTO>> getSellers(){
        List<Seller> sellers = service.getSellers();

        List<SellerDTO> sellerDTOS = new ArrayList<>();
        if(sellers == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        for(Seller seller : sellers) {
            SellerDTO seDTO = new SellerDTO(seller.getId(), seller.getName(),
                    seller.getPhoneNumber(), seller.getEmail());
            seDTO.add(linkTo(methodOn(SellerController.class).getSeller(seDTO.getId())).withSelfRel());
            sellerDTOS.add(seDTO);
        }

        Link link = linkTo(methodOn(VehicleController.class).getVehicles(Optional.of(1),Optional.of(10))).withSelfRel();
        CollectionModel<SellerDTO> resp = CollectionModel.of(sellerDTOS, link);

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/seller/{id}")
    public ResponseEntity<SellerDTO> getSeller(@PathVariable("id") long id) {

        Seller seller = service.getSellerById(id);

        if(seller == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        SellerDTO seDTO = new SellerDTO(seller.getId(), seller.getName(),
                seller.getPhoneNumber(), seller.getEmail());

        return new ResponseEntity<>(seDTO, HttpStatus.OK);
    }


    @PostMapping(value = "/seller", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SellerDTO> addSeller(@RequestBody SellerDTO sellerDTO) {
        Seller seller = service.addSeller(sellerDTO);

        if(seller == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

         SellerDTO seDTO = new SellerDTO(seller.getId(), seller.getName(),
                 seller.getPhoneNumber(), seller.getEmail());

        seDTO.add(linkTo(methodOn(SellerController.class).getSellers()).withRel("all_vehicles"));
        seDTO.add(linkTo(methodOn(SellerController.class).getSeller(seDTO.getId())).withRel("vehicle_by_VIN"));
        seDTO.add(linkTo(methodOn(SellerController.class).updateSeller(seDTO.getId(), seDTO)).withRel("update"));
        seDTO.add(linkTo(methodOn(SellerController.class).deleteSeller(seDTO.getId())).withRel("delete"));

        return new ResponseEntity<>(seDTO, HttpStatus.CREATED);
    }


    @PutMapping(value ="/seller/{id}", consumes = "application/json", produces = "application/json")
    ResponseEntity<SellerDTO> updateSeller(@PathVariable("id") long id, @RequestBody SellerDTO sellerDTO) {
        if(sellerDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if(service.getSellerById(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        Seller seller = service.updateSeller(id, sellerDTO);

        SellerDTO seDTO = new SellerDTO(seller.getId(), seller.getName(),
                seller.getPhoneNumber(), seller.getEmail());

        return new ResponseEntity<>(seDTO, HttpStatus.OK);
    }


    @DeleteMapping(value = "/seller/{id}", produces =  "application/json")
    ResponseEntity<SellerDTO> deleteSeller(@PathVariable("id") long id) {
        if(service.getSellerById(id) == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Seller seller = service.deleteSeller(id);

        SellerDTO seDTO = new SellerDTO(seller.getId(), seller.getName(),
                seller.getPhoneNumber(), seller.getEmail());

        return new ResponseEntity<>(seDTO, HttpStatus.OK);
    }
}
