package com.example.StandardCars.services;

import com.example.StandardCars.Repository.BrandRepository;
import com.example.StandardCars.dto.BrandDTO;
import com.example.StandardCars.model.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    BrandRepository brandRepository;

    public Brand addBrand(BrandDTO brandDTO){
        Brand brand = brandRepository.save(new Brand(brandDTO.getId(), brandDTO.getName(), brandDTO.getCountry()));

        return brand;
    }

    public List<Brand> getBrands(){
        return brandRepository.findAll();
    }

    public Brand getBrand(long id){
        return brandRepository.findById(id).get();
    }

}
