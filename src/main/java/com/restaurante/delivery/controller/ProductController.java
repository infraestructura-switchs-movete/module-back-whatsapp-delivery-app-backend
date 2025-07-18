package com.restaurante.delivery.controller;


import com.restaurante.delivery.business.interfaces.IProductBusiness;
import com.restaurante.delivery.dto.request.GenericResponse;
import com.restaurante.delivery.dto.request.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

    @RestController
    @RequestMapping("/${app.request.prefix}/${app.request.version}${app.request.mappings}/product")
    @RequiredArgsConstructor(onConstructor_ = @Autowired)
    @CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
    @Slf4j
    public class ProductController {

        private final IProductBusiness productBusiness;

        @PostMapping("/create")
        public ResponseEntity<ProductDto> save(
                @RequestParam("image") MultipartFile image,
                @RequestParam("productName") String productName,
                @RequestParam("description") String description,
                @RequestParam("price") Double price,
                @RequestParam("categoryId") Long categoryId) {
            ProductDto requestDTO = new ProductDto();
            requestDTO.setProductName(productName);
            requestDTO.setDescription(description);
            requestDTO.setPrice(price);
            requestDTO.setCategoryId(categoryId);

            ProductDto savedProduct = productBusiness.save(requestDTO, image);
            return ResponseEntity.ok(savedProduct);
        }



        @PutMapping("/update/{id}")
        public ResponseEntity<GenericResponse> update(@PathVariable("id") Long id,
                                                      @RequestParam(value = "image", required = false) MultipartFile image,
                                                      @RequestParam("productName") String productName,
                                                      @RequestParam("description") String description,
                                                      @RequestParam("price") Double price,
                                                      @RequestParam("categoryId") Long categoryId){

            ProductDto productRequestDTO = new ProductDto();
            productRequestDTO.setProductName(productName);
            productRequestDTO.setDescription(description);
            productRequestDTO.setPrice(price);
            productRequestDTO.setCategoryId(categoryId);

            GenericResponse response = productBusiness.update(id, productRequestDTO, image);

            return ResponseEntity.ok(response);
        }

        @GetMapping
        public ResponseEntity<List<ProductDto>> getActiveProducts() {
            log.info("Obteniendo productos activos");
            List<ProductDto> activeProducts = productBusiness.getProduct();
            return ResponseEntity.ok(activeProducts);
        }


        @DeleteMapping("/delete/{id}")
        public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
            productBusiness.delete(id);
            return ResponseEntity.noContent().build();
        }


    }







