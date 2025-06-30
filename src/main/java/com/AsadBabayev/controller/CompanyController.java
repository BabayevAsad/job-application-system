package com.AsadBabayev.controller;

import com.AsadBabayev.dto.company.CompanyDto;
import com.AsadBabayev.dto.company.CompanyRequestDto;

import java.util.List;

public interface CompanyController {

    List<CompanyDto> getAllCompany();

    CompanyDto getCompanyById(int id);

    CompanyDto saveCompany(CompanyRequestDto companyRequestDto);

    CompanyDto updateCompany(CompanyRequestDto companyRequestDto, int id);

    void deleteCompany(int id);
}
