package com.restaurante.delivery.dto.request;

import com.restaurante.delivery.dto.SellProducts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class OrderDetailsDTO {

    private String phone;
    private List<SellProducts> products;
    private Double total;
    private String method;
    private String address;
    private Long paymentId;
    private String nameClient;
    private String phoneClient;
    private String mail;
    private Long typeIdentificationId;
    private Long nameIdentification;

}
