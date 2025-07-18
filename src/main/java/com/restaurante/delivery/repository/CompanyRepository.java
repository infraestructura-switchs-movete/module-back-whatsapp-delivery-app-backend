package com.restaurante.delivery.repository;


import com.restaurante.delivery.dto.request.CompanyDto;
import com.restaurante.delivery.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query(value = "SELECT new com.restaurante.delivery.dto.request.CompanyDto(c.companyId, c.name, c.logo, c.numberWhatsapp, c.longitude, c.latitude, c.baseValue, c.additionalValue) " +
            "FROM Company c " +
            "WHERE c.status = 'ACTIVE'",
            countQuery = "SELECT COUNT(*) " +
                    "FROM Company c " +
                    "WHERE c.status = 'ACTIVE'")
    List<CompanyDto> getAllCompany();


}
