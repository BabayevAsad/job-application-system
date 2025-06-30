package com.AsadBabayev.controller.impl;

import com.AsadBabayev.controller.JobController;
import com.AsadBabayev.dto.job.JobDto;
import com.AsadBabayev.dto.job.JobRequestDto;
import com.AsadBabayev.services.JobService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/job")
public class JobControllerImpl implements JobController {

    private final JobService jobService;

    public JobControllerImpl(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/getAll")
    @Override
    public List<JobDto> getAllJob() {
        return jobService.getAllJob();
    }

    @GetMapping("/getById/{id}")
    @Override
    public JobDto getJobById(@PathVariable int id) {
        return jobService.getJobById(id);
    }

    @PostMapping("/save")
    @Override
    public JobDto saveJob(@RequestBody @Valid JobRequestDto jobRequestDto) {
        return jobService.saveJob(jobRequestDto);
    }

    @PutMapping("/update/{id}")
    @Override
    public JobDto updateJob(@RequestBody @Valid JobRequestDto jobRequestDto, @PathVariable int id) {
        return jobService.updateJob(jobRequestDto, id);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public void deleteJob(@PathVariable int id) {
        jobService.deleteJob(id);
    }
}
