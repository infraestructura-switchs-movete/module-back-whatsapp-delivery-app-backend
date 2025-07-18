package com.restaurante.delivery.business.interfaces;



import com.restaurante.delivery.dto.request.CompanyDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ICompanyBusiness {

    @Transactional
    CompanyDto save(CompanyDto companyRequest, MultipartFile logoFile);

    List<CompanyDto> getAllCompany();

    Boolean delete(Long id);




}
