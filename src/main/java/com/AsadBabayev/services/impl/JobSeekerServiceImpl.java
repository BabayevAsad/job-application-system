package com.AsadBabayev.services.impl;

import com.AsadBabayev.dto.JobSeeker.JobSeekerDto;
import com.AsadBabayev.dto.JobSeeker.JobSeekerRequestDto;
import com.AsadBabayev.entities.JobSeeker;
import com.AsadBabayev.repository.JobSeekerRepository;
import com.AsadBabayev.services.JobSeekerService;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobSeekerServiceImpl implements JobSeekerService {

    private final JobSeekerRepository jobSeekerRepository;

    public JobSeekerServiceImpl(JobSeekerRepository jobSeekerRepository) {
        this.jobSeekerRepository = jobSeekerRepository;
    }

    @Override
    @Cacheable(value = "jobSeekers", key = "'all'")
    public List<JobSeekerDto> getAllJobSeeker() {
        List<JobSeekerDto> list = new ArrayList<>();
        List<JobSeeker> jobSeekerList = jobSeekerRepository.findAll();

        for (JobSeeker jobSeeker : jobSeekerList) {
            JobSeekerDto jobSeekerDto = new JobSeekerDto();
            BeanUtils.copyProperties(jobSeeker, jobSeekerDto);
            list.add(jobSeekerDto);
        }

        return list;
    }

    @Override
    @Cacheable(value = "jobSeekers", key = "#id")
    public JobSeekerDto getJobSeekerById(int id) {
        Optional<JobSeeker> optional = jobSeekerRepository.findById(id);

        if (optional.isPresent()) {
            JobSeekerDto jobSeekerDto = new JobSeekerDto();
            BeanUtils.copyProperties(optional.get(), jobSeekerDto);

            return jobSeekerDto;
        }

        return null;
    }

    @Override
    @CachePut(value = "jobSeekers", key = "#result.id")
    @CacheEvict(value = "jobSeekers", key = "'all'")
    public JobSeekerDto saveJobSeeker(JobSeekerRequestDto jobSeekerRequestDto) {
        JobSeeker jobSeeker = new JobSeeker();

        BeanUtils.copyProperties(jobSeekerRequestDto, jobSeeker);
        JobSeeker savedJobSeeker = jobSeekerRepository.save(jobSeeker);

        JobSeekerDto response = new JobSeekerDto();
        BeanUtils.copyProperties(savedJobSeeker, response);

        return response;
    }

    @Override
    @CachePut(value = "jobSeekers", key = "#result.id")
    @CacheEvict(value = "jobSeekers", key = "'all'")
    public JobSeekerDto updateJobSeeker(int id, JobSeekerRequestDto jobSeekerRequestDto) {
        Optional<JobSeeker> optional = jobSeekerRepository.findById(id);

        if (optional.isPresent()) {
            JobSeekerDto jobSeekerDto = new JobSeekerDto();
            JobSeeker currentJobSeeker = optional.get();

            currentJobSeeker.setFirstName(jobSeekerRequestDto.getFirstName());
            currentJobSeeker.setLastName(jobSeekerRequestDto.getLastName());
            currentJobSeeker.setBirthDate(jobSeekerRequestDto.getBirthDate());
            currentJobSeeker.setNationality(jobSeekerRequestDto.getNationality());
            currentJobSeeker.setEmail(jobSeekerRequestDto.getEmail());
            currentJobSeeker.setPhone(jobSeekerRequestDto.getPhone());
            currentJobSeeker.setGender(jobSeekerRequestDto.getGender());
            currentJobSeeker.setCountry(jobSeekerRequestDto.getCountry());
            currentJobSeeker.setCity(jobSeekerRequestDto.getCity());
            currentJobSeeker.setAddress(jobSeekerRequestDto.getAddress());
            currentJobSeeker.setPostalCode(jobSeekerRequestDto.getPostalCode());

            jobSeekerRepository.save(currentJobSeeker);
            BeanUtils.copyProperties(currentJobSeeker, jobSeekerDto);

            return jobSeekerDto;
        }

        return null;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "jobSeekers", key = "#id"),
            @CacheEvict(value = "jobSeekers", key = "'all'")
    })
    public void deleteJobSeekerById(int id) {
        jobSeekerRepository.deleteById(id);
    }
}
