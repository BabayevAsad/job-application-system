package com.AsadBabayev.services;

import com.AsadBabayev.dto.job.JobDto;
import com.AsadBabayev.dto.job.JobRequestDto;

import java.util.List;

public interface JobService {

    List<JobDto> getAllJob();

    JobDto getJobById(int id);

    JobDto saveJob(JobRequestDto jobRequestDto);

    JobDto updateJob(JobRequestDto jobRequestDto, int id);

    void deleteJob(int id);
}
