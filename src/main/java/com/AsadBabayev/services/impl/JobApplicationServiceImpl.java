package com.AsadBabayev.services.impl;

import com.AsadBabayev.dto.JobSeeker.JobSeekerDto;
import com.AsadBabayev.dto.job.JobDto;
import com.AsadBabayev.dto.jobApplication.JobApplicationDto;
import com.AsadBabayev.dto.jobApplication.JobApplicationRequestDto;
import com.AsadBabayev.entities.Job;
import com.AsadBabayev.entities.JobApplication;
import com.AsadBabayev.entities.JobSeeker;
import com.AsadBabayev.repository.JobApplicationRepository;
import com.AsadBabayev.repository.JobRepository;
import com.AsadBabayev.repository.JobSeekerRepository;
import com.AsadBabayev.services.JobApplicationService;
import com.AsadBabayev.services.JobSeekerService;
import com.AsadBabayev.services.JobService;
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
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final JobSeekerService jobSeekerService;
    private final JobService jobService;
    private final JobRepository jobRepository;
    private final JobSeekerRepository jobSeekerRepository;

    public JobApplicationServiceImpl(
            JobApplicationRepository applicationRepository,
            JobSeekerService jobSeekerService,
            JobService jobService,
            JobRepository jobRepository,
            JobSeekerRepository jobSeekerRepository) {
        this.applicationRepository = applicationRepository;
        this.jobSeekerService = jobSeekerService;
        this.jobService = jobService;
        this.jobRepository = jobRepository;
        this.jobSeekerRepository = jobSeekerRepository;
    }

    @Override
    @Cacheable(value = "jobApplications", key = "'all'")
    public List<JobApplicationDto> getAllJobApplication() {
        List<JobApplication> jobApplicationList = applicationRepository.findAll();
        List<JobApplicationDto> jobApplicationDtoList = new ArrayList<>();

        for (JobApplication jobApplication : jobApplicationList) {
            JobApplicationDto jobApplicationDto = new JobApplicationDto();

            BeanUtils.copyProperties(jobApplication, jobApplicationDto, "jobSeekerDto", "jobDto");

            JobDto jobDto = jobService.getJobById(jobApplication.getJob().getId());
            JobSeekerDto jobSeekerDto = jobSeekerService.getJobSeekerById(jobApplication.getJobSeeker().getId());

            jobApplicationDto.setJobDto(jobDto);
            jobApplicationDto.setJobSeekerDto(jobSeekerDto);

            jobApplicationDtoList.add(jobApplicationDto);
        }

        return jobApplicationDtoList;
    }

    @Override
    @Cacheable(value = "jobApplications", key = "#id")
    public JobApplicationDto getJobApplicationById(int id) {
        Optional<JobApplication> optional = applicationRepository.findById(id);

        if (optional.isPresent()) {
            JobApplication dbJobApplication = optional.get();

            JobApplicationDto jobApplicationDto = new JobApplicationDto();

            BeanUtils.copyProperties(dbJobApplication, jobApplicationDto, "jobSeekerDto", "jobDto");

            JobDto jobDto = jobService.getJobById(dbJobApplication.getJob().getId());
            JobSeekerDto jobSeekerDto = jobSeekerService.getJobSeekerById(dbJobApplication.getJobSeeker().getId());

            jobApplicationDto.setJobDto(jobDto);
            jobApplicationDto.setJobSeekerDto(jobSeekerDto);

            return jobApplicationDto;
        }

        return null;
    }

    @Override
    @CachePut(value = "jobApplications", key = "#result.id")
    @CacheEvict(value = "jobApplications", key = "'all'")
    public JobApplicationDto saveJobApplication(JobApplicationRequestDto jobApplicationRequestDto) {
        JobApplication dbJobApplication = new JobApplication();

        BeanUtils.copyProperties(jobApplicationRequestDto, dbJobApplication, "jobSeeker", "job");

        System.out.println(jobApplicationRequestDto.getJobId());
        System.out.println(jobApplicationRequestDto.getJobSeekerId());

        Optional<Job> optionalJob = jobRepository.findById(jobApplicationRequestDto.getJobId());
        Optional<JobSeeker> optionalJobSeeker = jobSeekerRepository.findById((jobApplicationRequestDto.getJobSeekerId()));

        if (optionalJob.isPresent() && optionalJobSeeker.isPresent()) {
            Job dbJob = optionalJob.get();
            JobSeeker dbJobSeeker = optionalJobSeeker.get();

            dbJobApplication.setJob(dbJob);
            dbJobApplication.setJobSeeker(dbJobSeeker);
            applicationRepository.save(dbJobApplication);

            JobApplicationDto response = new JobApplicationDto();

            BeanUtils.copyProperties(dbJobApplication, response, "jobSeekerDto", "jobDto");
            JobDto jobDto = jobService.getJobById(jobApplicationRequestDto.getJobId());
            JobSeekerDto jobSeekerDto = jobSeekerService.getJobSeekerById((jobApplicationRequestDto.getJobSeekerId()));

            response.setJobDto(jobDto);
            response.setJobSeekerDto(jobSeekerDto);

            return response;
        }

        return null;
    }

    @Override
    @CachePut(value = "jobApplications", key = "#result.id")
    @CacheEvict(value = "jobApplications", key = "'all'")
    public JobApplicationDto updateJobApplication(JobApplicationRequestDto jobApplicationRequestDto, int id) {
        Optional<JobApplication> optional = applicationRepository.findById(id);

        if (optional.isPresent()) {
            JobApplication dbJobApplication = optional.get();

            BeanUtils.copyProperties(jobApplicationRequestDto, dbJobApplication, "job", "jobSeeker");

            Job dbJob = jobRepository.findById(jobApplicationRequestDto.getJobId()).get();
            JobSeeker dbJobSeeker = jobSeekerRepository.findById(jobApplicationRequestDto.getJobSeekerId()).get();

            dbJobApplication.setJob(dbJob);
            dbJobApplication.setJobSeeker(dbJobSeeker);

            JobApplication savedJobApplication = applicationRepository.save(dbJobApplication);

            JobApplicationDto response = new JobApplicationDto();

            BeanUtils.copyProperties(savedJobApplication, response, "jobSeekerDto", "jobDto");

            JobDto jobDto = jobService.getJobById(jobApplicationRequestDto.getJobId());
            JobSeekerDto jobSeekerDto = jobSeekerService.getJobSeekerById((jobApplicationRequestDto.getJobSeekerId()));

            response.setJobDto(jobDto);
            response.setJobSeekerDto(jobSeekerDto);

            return response;
        }

        return null;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "jobApplications", key = "#id"),
            @CacheEvict(value = "jobApplications", key = "'all'")
    })
    public void deleteJobApplicationById(int id) {
        applicationRepository.deleteById(id);
    }
}
