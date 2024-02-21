package com.example.StandardCars.services;

import com.example.StandardCars.Repository.BrandRepository;
import com.example.StandardCars.dto.BrandDTO;
import com.example.StandardCars.model.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public Page<BrandDTO> getBrands(int page, int size, String sort){
        return brandRepository.findAll(PageRequest.of(page,size, Sort.by(sort))).map(BrandDTO::toBrandDTO);
    }

    public Brand getBrand(long id){
        return brandRepository.findById(id).get();
    }

    public Brand updateBrand(long id, BrandDTO brandDTO){
        Brand brand = brandRepository.findById(id).get();

        if(brand == null){ return null; }

        brandDTO.setId(id);
        brandRepository.save(new Brand(brandDTO.getId(), brandDTO.getName(), brandDTO.getCountry()));

        return brand;
    }

    public Brand deleteBrand(long id) {

        Brand brand = brandRepository.findById(id).get();

        if(brand == null){
            return null;
        }

        brandRepository.delete(brand);

        return brand;
    }

    public Brand getBrandByName(String name){
        Brand brand = brandRepository.findBrandByName(name);
        if(brand == null){
            return null;
        }
        return brand;
    }

}
