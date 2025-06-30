package com.AsadBabayev.services;

import com.AsadBabayev.dto.JobSeeker.JobSeekerDto;
import com.AsadBabayev.dto.JobSeeker.JobSeekerRequestDto;

import java.util.List;

public interface JobSeekerService {
    List<JobSeekerDto> getAllJobSeeker();

    JobSeekerDto getJobSeekerById(int id);

    JobSeekerDto saveJobSeeker(JobSeekerRequestDto jobSeekerRequestDto);

    JobSeekerDto updateJobSeeker(int id, JobSeekerRequestDto jobSeekerRequestDto);

    void deleteJobSeekerById(int id);
}
