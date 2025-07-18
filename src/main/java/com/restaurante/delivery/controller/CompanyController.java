package com.restaurante.delivery.controller;


import com.restaurante.delivery.business.interfaces.ICompanyBusiness;

import com.restaurante.delivery.dto.request.CompanyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/${app.request.prefix}/${app.request.version}${app.request.mappings}/company")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@Slf4j
public class CompanyController {

    private final ICompanyBusiness companyService;

    @PostMapping("/create")
    public ResponseEntity<CompanyDto> createCompany(
            @RequestParam("nameCompany") String nameCompany,
            @RequestParam("numberWhatsapp") Long numberWhatsapp,
            @RequestParam("longitude") String longitude,
            @RequestParam("latitude") String latitude,
            @RequestParam("baseValue") Double baseValue,
            @RequestParam("additionalValue") Double additionalValue,
            @RequestParam("logo") MultipartFile logo) {

        CompanyDto CompanyDto = new CompanyDto();
        CompanyDto.setNameCompany(nameCompany);
        CompanyDto.setNumberWhatsapp(numberWhatsapp);
        CompanyDto.setLongitude(longitude);
        CompanyDto.setLatitude(latitude);
        CompanyDto.setBaseValue(baseValue);
        CompanyDto.setAdditionalValue(additionalValue);

        CompanyDto savedCompany = companyService.save(CompanyDto, logo);

        return ResponseEntity.ok(savedCompany);
    }


    @GetMapping("/get-company")
    public ResponseEntity<List<CompanyDto>> getAllCategories() {
        log.info("Iniciando endpoint para obtener todas las compañias");
        List<CompanyDto> categories = companyService.getAllCompany();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        companyService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
