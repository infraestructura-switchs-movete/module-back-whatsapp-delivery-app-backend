package com.restaurante.delivery.business.service;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.restaurante.delivery.business.interfaces.IProductBusiness;
import com.restaurante.delivery.dto.request.GenericResponse;
import com.restaurante.delivery.dto.request.ProductDto;
import com.restaurante.delivery.exception.CustomErrorException;
import com.restaurante.delivery.model.Category;
import com.restaurante.delivery.model.Product;
import com.restaurante.delivery.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class ProductService implements IProductBusiness {

    private final ProductRepository repository;
    private final CategoryService categoryService;
    private final Cloudinary cloudinary;

    @Transactional
    @Override
    public ProductDto save(ProductDto productResponseDTO, MultipartFile imageFile) {
        try {
            Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            String imageUrl = (String) uploadResult.get("url");

            Product product = new Product();
            product.setName(productResponseDTO.getProductName());
            product.setPrice(productResponseDTO.getPrice());
            product.setDescription(productResponseDTO.getDescription());
            product.setCategoryId(productResponseDTO.getCategoryId());
            product.setStatus("ACTIVE");
            product.setImage(imageUrl);

            Product newProduct = repository.save(product);

            ProductDto response = new ProductDto();
            BeanUtils.copyProperties(newProduct, response);
            return response;
        } catch (IOException e) {
            log.error("Error al subir la imagen", e);
            throw new RuntimeException("Error al subir la imagen", e);
        }
    }

    @Override
    public List<ProductDto> getProduct() {
        return repository.getProducts();
    }




    @Override
    public GenericResponse update(Long id, ProductDto requestDTO, MultipartFile imageFile)  {
        try {
            Optional<Product> productOptional = repository.findById(id);
            if (!productOptional.isPresent()) {
                throw new CustomErrorException(HttpStatus.BAD_REQUEST, "Producto no encontrado");
            }

            Product product = productOptional.get();
            product.setName(requestDTO.getProductName());
            product.setPrice(requestDTO.getPrice());
            product.setDescription(requestDTO.getDescription());
            product.setCategoryId(requestDTO.getCategoryId());
            product.setStatus("ACTIVE");

            if (imageFile != null && !imageFile.isEmpty()) {
                Map uploadResult = cloudinary.uploader().upload(imageFile.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
                String imageUrl = (String) uploadResult.get("url");
                product.setImage(imageUrl);
            }

            repository.save(product);
            return new GenericResponse("Producto actualizado correctamente", 200);
        } catch (IOException e) {
            log.error("Error al subir la imagen", e);
            throw new RuntimeException("Error al subir la imagen", e);
        }
    }

    @Override
    public Boolean delete(Long id) {
        if (repository.existsById(id)) {
            Product product = repository.findById(id).get();
            product.setStatus("INACTIVE");
            repository.save(product);
            return true;
        } else {
            throw new RuntimeException("el producto no fue encontrada por el id " + id);
        }
    }





}
