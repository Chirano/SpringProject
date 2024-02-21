package com.example.StandardCars.services;
import com.example.StandardCars.Repository.BrandRepository;
import com.example.StandardCars.Repository.ModelRepository;
import com.example.StandardCars.dto.BrandDTO;
import com.example.StandardCars.dto.ModelDTO;
import com.example.StandardCars.model.Brand;
import com.example.StandardCars.model.Model;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelService {

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    BrandRepository brandRepository;

    @Transactional
    public Model addModel(ModelDTO modelDTO){
        Brand brand = brandRepository.findBrandByName(modelDTO.getBrand());

        Model model = modelRepository.save(new Model(modelDTO.getId(), modelDTO.getName(), brand));

        return model;
    }

    public Page<ModelDTO> getModels(int page, int size, String sort){
        return modelRepository.findAll(PageRequest.of(page, size, Sort.by(sort))).map(ModelDTO::toModelDTO);
    }

    public Model getModel(long id){
        return modelRepository.findById(id).get();
    }


    @Transactional
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

    @Transactional
    public Model getModelByName(String name){
        Model model = modelRepository.findModelByName(name);
        return model;
    }

}
