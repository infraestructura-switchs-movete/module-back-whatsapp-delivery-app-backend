package com.restaurante.delivery.business.interfaces;


import com.restaurante.delivery.dto.request.CategoryDto;
import com.restaurante.delivery.dto.request.GenericResponse;
import com.restaurante.delivery.model.Category;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ICategoryBusiness {

    @Transactional
    CategoryDto save(CategoryDto categoryDto);

    List<CategoryDto> getAllCategories();

    GenericResponse update(Long id, CategoryDto requestDTO);
    Boolean delete(Long id);




}
