package com.restaurante.delivery.business.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.restaurante.delivery.business.interfaces.ICompanyBusiness;
import com.restaurante.delivery.dto.request.CompanyDto;
import com.restaurante.delivery.model.Company;
import com.restaurante.delivery.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class CompanyService implements ICompanyBusiness {

    private final CompanyRepository companyRepository;
    private final Cloudinary cloudinary;

    @Override
    public CompanyDto save(CompanyDto CompanyDto, MultipartFile logoFile) {
        try {
            Map uploadResult = cloudinary.uploader().upload(logoFile.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            String logoUrl = (String) uploadResult.get("url");

            Company company = new Company();
            company.setName(CompanyDto.getNameCompany());
            company.setNumberWhatsapp(CompanyDto.getNumberWhatsapp());
            company.setLongitude(CompanyDto.getLongitude());
            company.setLatitude(CompanyDto.getLatitude());
            company.setBaseValue(CompanyDto.getBaseValue());
            company.setAdditionalValue(CompanyDto.getAdditionalValue());
            company.setLogo(logoUrl);
            company.setStatus("ACTIVE");

            Company savedCompany = companyRepository.save(company);

            CompanyDto.setCompanyId(savedCompany.getCompanyId());
            CompanyDto.setLogoUrl(savedCompany.getLogo());

            return CompanyDto;

        } catch (IOException e) {
            log.error("Error al subir la imagen del logo", e);
            throw new RuntimeException("Error al subir la imagen del logo", e);
        }
    }

    @Override
    public List<CompanyDto> getAllCompany() {
        return companyRepository.getAllCompany();
    }

    @Override
    public Boolean delete(Long id) {
        if (companyRepository.existsById(id)) {
            Company company = companyRepository.findById(id).get();
            company.setStatus("INACTIVE");
            companyRepository.save(company);
            return true;
        } else {
            throw new RuntimeException("La compañia no fue encontrada por el id " + id);
        }
    }

}
