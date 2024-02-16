package com.example.StandardCars.Repository;

import com.example.StandardCars.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    Seller findSellerByName(String name);

}
