package com.AsadBabayev.controller;

import com.AsadBabayev.dto.JobSeeker.JobSeekerDto;
import com.AsadBabayev.dto.JobSeeker.JobSeekerRequestDto;

import java.util.List;
import java.util.Optional;

public interface JobSeekerController {
    List<JobSeekerDto> getAllJobSeeker();

    Optional<JobSeekerDto> getJobSeekerById(int id);

    JobSeekerDto saveJobSeeker(JobSeekerRequestDto jobSeekerRequestDto);

    JobSeekerDto updateJobSeeker(int id, JobSeekerRequestDto jobSeekerRequestDto);

    void deleteJobSeeker(int id);
}
