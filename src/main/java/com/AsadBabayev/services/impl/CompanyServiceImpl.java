package com.AsadBabayev.services.impl;

import com.AsadBabayev.dto.company.CompanyDto;
import com.AsadBabayev.dto.company.CompanyRequestDto;
import com.AsadBabayev.dto.job.JobActionDto;
import com.AsadBabayev.entities.Company;
import com.AsadBabayev.repostory.CompanyRepository;
import com.AsadBabayev.repostory.JobRepository;
import com.AsadBabayev.services.CompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository repository;
    private final JobRepository jobRepository;

    public CompanyServiceImpl(CompanyRepository repository, JobRepository jobRepository) {
        this.repository = repository;
        this.jobRepository = jobRepository;
    }

    @Override
    public List<CompanyDto> getAllCompany() {
        List<CompanyDto> companyDtoList = new ArrayList<>();
        List<Company> companyList = repository.findAll();

        for (Company company : companyList) {
            CompanyDto companyDto = new CompanyDto();
            BeanUtils.copyProperties(company, companyDto);

            List<JobActionDto> jobActionDtoList = new ArrayList<>();

            jobRepository.findByCompanyId(company.getId()).forEach(job -> {
                JobActionDto jobActionDto = new JobActionDto();
                BeanUtils.copyProperties(job, jobActionDto);
                jobActionDtoList.add(jobActionDto);
            });

            companyDto.setJobActionDtoList(jobActionDtoList);


            companyDtoList.add(companyDto);
        }

        return companyDtoList;
    }

    @Override
    public CompanyDto getCompanyById(int id) {
        Optional<Company> optional = repository.findById(id);

        if (optional.isPresent()) {
            Company dbCompany = optional.get();
            CompanyDto response = new CompanyDto();

            List<JobActionDto> jobActionDtoList = new ArrayList<>();

            jobRepository.findByCompanyId(id).forEach(job -> {
                JobActionDto jobActionDto = new JobActionDto();
                BeanUtils.copyProperties(job, jobActionDto);
                jobActionDtoList.add(jobActionDto);
            });

            response.setJobActionDtoList(jobActionDtoList);

            BeanUtils.copyProperties(dbCompany, response);
            return response;
        }

        return null;
    }

    @Override
    public CompanyDto saveCompany(CompanyRequestDto companyRequestDto) {
        Company company = new Company();

        BeanUtils.copyProperties(companyRequestDto, company);
        Company savedCompany = repository.save(company);

        CompanyDto response = new CompanyDto();
        BeanUtils.copyProperties(savedCompany, response);

        return response;
    }

    @Override
    public CompanyDto updateCompany(CompanyRequestDto companyRequestDto, int id) {
        Optional<Company> optional = repository.findById(id);

        if (optional.isPresent()) {
            Company company = optional.get();
            BeanUtils.copyProperties(companyRequestDto, company);

            Company savedCompany = repository.save(company);
            CompanyDto response = new CompanyDto();
            BeanUtils.copyProperties(savedCompany, response);

            return response;
        }

        return null;
    }

    @Override
    public void deleteCompany(int id) {
        repository.deleteById(id);
    }
}
