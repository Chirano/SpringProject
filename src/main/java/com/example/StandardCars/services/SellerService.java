package com.example.StandardCars.services;

import com.example.StandardCars.Repository.SellerRepository;
import com.example.StandardCars.dto.SellerDTO;
import com.example.StandardCars.model.Seller;
import com.example.StandardCars.model.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {

    @Autowired
    SellerRepository repository;

    public List<Seller> getSellers(){
        return repository.findAll();
    }


    public Seller getSellerById(long id){
        return repository.findById(id).get();
    }

   public Seller addSeller(SellerDTO sellerDTO){
        Seller newSeller = repository.save(new Seller(sellerDTO.getId(), sellerDTO.getName(),
                sellerDTO.getPhoneNumber(), sellerDTO.getEmail()));

        return newSeller;
   }

   public Seller updateSeller(long id, SellerDTO sellerDTO){
       Seller seller = repository.findById(id).get();

       sellerDTO.setId(id);
       Seller upSeller = new Seller(sellerDTO.getId(), sellerDTO.getName(),
               sellerDTO.getPhoneNumber(), sellerDTO.getEmail());

       return  upSeller;
   }


   public Seller deleteSeller(long id){
        Seller seller = repository.findById(id).get();
        if(seller == null){
            return null;
        }
        repository.delete(seller);
        return seller;

   }


}
