package com.example.StandardCars.Repository;

import com.example.StandardCars.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model, Long> {
    Model findModelByName(String name);
}
