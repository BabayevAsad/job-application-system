package com.AsadBabayev.controller;

import com.AsadBabayev.dto.jobApplication.JobApplicationDto;
import com.AsadBabayev.dto.jobApplication.JobApplicationRequestDto;

import java.util.List;

public interface JobApplicationController {

    List<JobApplicationDto> getAllJobApplication();

    JobApplicationDto getJobApplicationById(int id);

    JobApplicationDto saveJobApplication(JobApplicationRequestDto jobApplicationRequestDto);

    JobApplicationDto updateJobApplication(JobApplicationRequestDto jobApplicationRequestDto, int id);

    void deleteJobApplicationById(int id);
}
