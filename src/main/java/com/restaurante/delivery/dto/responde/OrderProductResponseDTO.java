package com.restaurante.delivery.dto.responde;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductResponseDTO {


    private Long orderTransactionDeliveryId;
    private Long productId;
    private String name;
    private Long quantity;
    private Double price;
}
