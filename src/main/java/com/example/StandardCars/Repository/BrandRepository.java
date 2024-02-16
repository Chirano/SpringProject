package com.example.StandardCars.Repository;

import com.example.StandardCars.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Brand findBrandByName(String name);
}
