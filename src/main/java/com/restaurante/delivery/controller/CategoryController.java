package com.restaurante.delivery.controller;


import com.restaurante.delivery.business.interfaces.ICategoryBusiness;
import com.restaurante.delivery.dto.request.CategoryDto;
import com.restaurante.delivery.dto.request.GenericResponse;
import com.restaurante.delivery.model.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/${app.request.prefix}/${app.request.version}${app.request.mappings}/category")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@Slf4j
public class CategoryController {

    private final ICategoryBusiness iCategoryBusiness;

    @PostMapping("/create")
    public ResponseEntity<CategoryDto> save(@RequestBody CategoryDto requestDTO) {
        CategoryDto savedClient = iCategoryBusiness.save(requestDTO);
        return ResponseEntity.ok(savedClient);
    }

    @GetMapping("/no-page/getAllCategories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        log.info("Iniciando endpoint para obtener todas las categorías");
        List<CategoryDto> categories = iCategoryBusiness.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GenericResponse> update(@PathVariable("id") Long id,
                                                  @RequestBody CategoryDto requestDTO) {
        log.info("Iniciando actualización para Categoria con ID: {} y DTO: {}", id, requestDTO);
        GenericResponse response = iCategoryBusiness.update(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        iCategoryBusiness.delete(id);
        return ResponseEntity.noContent().build();
    }


}
