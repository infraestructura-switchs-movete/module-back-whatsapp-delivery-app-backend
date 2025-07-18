package com.restaurante.delivery.business.interfaces;

import com.restaurante.delivery.dto.request.GenericResponse;
import com.restaurante.delivery.dto.request.ProductDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductBusiness {
    @Transactional
    ProductDto save(ProductDto productResponseDTO, MultipartFile imageFile);

    List<ProductDto> getProduct();

    GenericResponse update(Long id, ProductDto requestDTO, MultipartFile imageFile);
    Boolean delete(Long id);

}
