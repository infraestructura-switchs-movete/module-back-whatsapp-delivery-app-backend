package com.restaurante.delivery.repository;

import com.restaurante.delivery.dto.request.ProductDto;
import com.restaurante.delivery.model.Category;
import com.restaurante.delivery.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatus(String status);

    long countByCategoryIdAndStatus(Long categoryId, String status);

    @Query("""
    SELECT new com.restaurante.delivery.dto.request.ProductDto(
        p.productId,
        p.name,
        p.price,
        p.description,
        p.status,
        p.image,
        c.id,
        c.name 
    )
    FROM Product p
    LEFT JOIN Category c ON p.categoryId = c.id
    WHERE p.status = 'ACTIVE'
""")
    List<ProductDto> getProducts();




}
