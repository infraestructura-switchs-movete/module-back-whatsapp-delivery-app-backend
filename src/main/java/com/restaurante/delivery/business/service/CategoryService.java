package com.restaurante.delivery.business.service;


import com.restaurante.delivery.business.interfaces.ICategoryBusiness;
import com.restaurante.delivery.dto.request.CategoryDto;
import com.restaurante.delivery.dto.request.GenericResponse;
import com.restaurante.delivery.exception.CustomErrorException;
import com.restaurante.delivery.model.Category;
import com.restaurante.delivery.repository.CategoryRepository;
import com.restaurante.delivery.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class CategoryService implements ICategoryBusiness {

    private final CategoryRepository repository;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public CategoryDto save(CategoryDto categoryDto) {

            Category category = new Category();
            category.setName(categoryDto.getName());
            category.setStatus("ACTIVE");
            Category newCategory = repository.save(category);

            CategoryDto response = new CategoryDto();
            BeanUtils.copyProperties(newCategory, response);
            return response;

    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = repository.findByStatus("ACTIVE");

        List<CategoryDto> response = new ArrayList<>();

        for (Category category : categories) {
            long numActiveProducts = productRepository.countByCategoryIdAndStatus(category.getId(), "ACTIVE");

            CategoryDto categoryWithProductCount = new CategoryDto(
                    category.getId(),
                    category.getName(),
                    category.getStatus(),
                    numActiveProducts
            );

            response.add(categoryWithProductCount);
        }

        return response;
    }



    @Override
    public GenericResponse update(Long id, CategoryDto requestDTO) {
        if (!repository.existsById(id)) throw new CustomErrorException(HttpStatus.BAD_REQUEST, "category no existe");

        Optional<Category> categoryOptional = repository.findById(id);

        Category category = categoryOptional.get();
        category.setName(requestDTO.getName());
        repository.save(category);

        return new GenericResponse("category actualizado con exito", 200);
    }

    @Override
    public Boolean delete(Long id) {
        if (repository.existsById(id)) {
            Category category = repository.findById(id).get();
            category.setStatus("INACTIVE");
            repository.save(category);
            return true;
        } else {
            throw new RuntimeException("el categorye no fue encontrada por el id " + id);
        }
    }

    public Optional<Category> findById(Long id) {
        return repository.findById(id);
    }





}
