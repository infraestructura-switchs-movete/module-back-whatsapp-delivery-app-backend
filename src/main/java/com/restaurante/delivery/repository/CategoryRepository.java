package com.restaurante.delivery.repository;

import com.restaurante.delivery.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByStatus(String status);


}
