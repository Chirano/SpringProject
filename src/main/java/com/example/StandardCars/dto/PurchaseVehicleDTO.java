package com.example.StandardCars.dto;

import com.example.StandardCars.Enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseVehicleDTO extends RepresentationModel<PurchaseVehicleDTO> {

    String buyerId;

    String transactionId;

}
