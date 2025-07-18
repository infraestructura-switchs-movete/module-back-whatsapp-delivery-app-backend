package com.restaurante.delivery.dto.responde;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {

    private Long productId;
    private String name;
    private Double price;
    private String categoryId;
}
