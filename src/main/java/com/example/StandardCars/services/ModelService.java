package com.example.StandardCars.services;
import com.example.StandardCars.Repository.BrandRepository;
import com.example.StandardCars.Repository.ModelRepository;
import com.example.StandardCars.dto.BrandDTO;
import com.example.StandardCars.dto.ModelDTO;
import com.example.StandardCars.model.Brand;
import com.example.StandardCars.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelService {

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    BrandRepository brandRepository;

    public Model addModel(ModelDTO modelDTO){
        Brand brand = brandRepository.findBrandByName(modelDTO.getBrand());

        Model model = modelRepository.save(new Model(modelDTO.getId(), modelDTO.getName(), brand));

        return model;
    }

    public List<Model> getModels(){
        return modelRepository.findAll();
    }

    public Model getModel(long id){
        return modelRepository.findById(id).get();
    }


    public Model updateModel(long id, ModelDTO modelDTO){
        Model model = modelRepository.findById(id).get();

        if(model == null){ return null; }

        modelDTO.setId(id);
        modelRepository.save(new Model(modelDTO));

        return model;
    }

    public Model deleteModel(long id) {

        Model model = modelRepository.findById(id).get();

        if(model == null){
            return null;
        }

        modelRepository.delete(model);

        return model;
    }

}
