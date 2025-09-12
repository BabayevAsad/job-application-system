package com.AsadBabayev.services.impl;

import com.AsadBabayev.dto.company.CompanyActionDto;
import com.AsadBabayev.dto.job.JobDto;
import com.AsadBabayev.dto.job.JobRequestDto;
import com.AsadBabayev.entities.Company;
import com.AsadBabayev.entities.Job;
import com.AsadBabayev.repository.CompanyRepository;
import com.AsadBabayev.repository.JobRepository;
import com.AsadBabayev.services.JobService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    private final JobRepository repository;
    private final CompanyRepository companyRepository;

    public JobServiceImpl(JobRepository repository, CompanyRepository companyRepository) {
        this.repository = repository;
        this.companyRepository = companyRepository;
    }

    @Override
    public List<JobDto> getAllJob() {
        List<JobDto> jobDtoList = new ArrayList<>();
        List<Job> jobList = repository.findAll();

        for (Job job : jobList) {
            JobDto jobDto = new JobDto();
            BeanUtils.copyProperties(job, jobDto, "companyActionDto");

            CompanyActionDto companyActionDto = new CompanyActionDto();

            Company dbcompany = companyRepository.findById(job.getCompany().getId()).get();
            BeanUtils.copyProperties(dbcompany, companyActionDto);

            jobDto.setCompanyActionDto(companyActionDto);
            jobDtoList.add(jobDto);
        }

        return jobDtoList;
    }

    @Override
    public JobDto getJobById(int id) {
        Optional<Job> optional = repository.findById(id);

        if (optional.isPresent()) {
            JobDto jobDto = new JobDto();
            Job dbJob = optional.get();

            int companyId = dbJob.getCompany().getId();
            Company company = companyRepository.findById(companyId).get();

            CompanyActionDto companyActionDto = new CompanyActionDto();

            BeanUtils.copyProperties(company, companyActionDto);

            BeanUtils.copyProperties(dbJob, jobDto, "companyActionDto");
            jobDto.setCompanyActionDto(companyActionDto);

            return jobDto;
        }

        return null;
    }

    @Override
    public JobDto saveJob(JobRequestDto jobRequestDto) {
        Job job = new Job();

        BeanUtils.copyProperties(jobRequestDto, job, "company");

        Company dbCompany = companyRepository.findById(jobRequestDto.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        job.setCompany(dbCompany);
        Job savedJob = repository.save(job);

        JobDto response = new JobDto();
        BeanUtils.copyProperties(savedJob, response, "companyActionDto");

        CompanyActionDto companyActionDto = new CompanyActionDto();
        BeanUtils.copyProperties(dbCompany, companyActionDto);

        response.setCompanyActionDto(companyActionDto);

        return response;
    }

    @Override
    public JobDto updateJob(JobRequestDto jobRequestDto, int id) {
        Optional<Job> optional = repository.findById(id);

        if (optional.isPresent()) {
            Job job = optional.get();
            BeanUtils.copyProperties(jobRequestDto, job, "company");

            Company dbCompany = companyRepository.findById(jobRequestDto.getCompanyId())
                    .orElseThrow(() -> new RuntimeException("Company not found"));

            job.setCompany(dbCompany);
            Job savedJob = repository.save(job);

            JobDto response = new JobDto();
            BeanUtils.copyProperties(savedJob, response, "companyActionDto");

            CompanyActionDto companyActionDto = new CompanyActionDto();
            BeanUtils.copyProperties(dbCompany, companyActionDto);

            response.setCompanyActionDto(companyActionDto);

            return response;
        }

        return null;
    }

    @Override
    public void deleteJob(int id) {
        repository.deleteById(id);
    }
}
