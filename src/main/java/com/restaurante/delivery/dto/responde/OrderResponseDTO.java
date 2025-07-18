package com.restaurante.delivery.dto.responde;

import com.restaurante.delivery.dto.SellProducts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

    private String phone;
    private Long orderTransactionDeliveryId;
    private List<SellProducts> products;
    private Double total;
    private Long paymentId;
    private String paymentName;
    private Long typeIdentificationId;
    private String typeIdentificationName;
    private String method;
    private String nameClient;
    private String address;
    private String phoneClient;
    private String mail;
    private Long numerIdentification;
    private String status;
    private String statusOrder;
}
