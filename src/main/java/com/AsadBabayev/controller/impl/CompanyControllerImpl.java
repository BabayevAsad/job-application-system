package com.AsadBabayev.controller.impl;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.AsadBabayev.controller.CompanyController;
import com.AsadBabayev.dto.company.CompanyDto;
import com.AsadBabayev.dto.company.CompanyRequestDto;
import com.AsadBabayev.services.CompanyService;

@RestController
@RequestMapping("/rest/api/company")
public class CompanyControllerImpl implements CompanyController {

    private final CompanyService companyService;

    public CompanyControllerImpl(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/getAll")
    @Override
    public List<CompanyDto> getAllCompany() {
        return companyService.getAllCompany();
    }

    @GetMapping("/getById/{id}")
    @Override
    public CompanyDto getCompanyById(@PathVariable int id) {
        return companyService.getCompanyById(id);
    }

    @PostMapping("/save")
    @Override
    public CompanyDto saveCompany(@RequestBody @Valid CompanyRequestDto companyRequestDto) {
        return companyService.saveCompany(companyRequestDto);
    }

    @PutMapping("/update/{id}")
    @Override
    public CompanyDto updateCompany(@RequestBody @Valid CompanyRequestDto companyRequestDto, @PathVariable int id) {
        return companyService.updateCompany(companyRequestDto, id);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public void deleteCompany(@PathVariable int id) {
        companyService.deleteCompany(id);
    }
}
