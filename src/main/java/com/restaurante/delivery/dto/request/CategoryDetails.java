package com.restaurante.delivery.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDetails {

    private String categoryName;
    private String imageCategory;
    private List<ProductResponseDTO> products;

    @Data
    public static class ProductResponseDTO {
        private Long productId;
        private String productName;
        private Double price;
        private String imageProduct;
    }
}
