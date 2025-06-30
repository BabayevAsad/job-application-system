package com.AsadBabayev.controller.impl;

import com.AsadBabayev.controller.JobApplicationController;
import com.AsadBabayev.dto.jobApplication.JobApplicationDto;
import com.AsadBabayev.dto.jobApplication.JobApplicationRequestDto;
import com.AsadBabayev.services.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/api/jobApplication")
public class JobApplicationControllerImpl implements JobApplicationController {

    private final JobApplicationService jobApplicationService;

    public JobApplicationControllerImpl(JobApplicationService jobApplicationService) {
        this.jobApplicationService = jobApplicationService;
    }

    @GetMapping("/getAll")
    @Override
    public List<JobApplicationDto> getAllJobApplication() {
        return jobApplicationService.getAllJobApplication();
    }

    @GetMapping("/getById/{id}")
    @Override
    public JobApplicationDto getJobApplicationById(@PathVariable int id) {
        return jobApplicationService.getJobApplicationById(id);
    }

    @PostMapping("/save")
    @Override
    public JobApplicationDto saveJobApplication(@RequestBody @Valid JobApplicationRequestDto jobApplicationRequestDto) {
        return jobApplicationService.saveJobApplication(jobApplicationRequestDto);
    }

    @PutMapping("/update/{id}")
    @Override
    public JobApplicationDto updateJobApplication(@RequestBody @Valid JobApplicationRequestDto jobApplicationRequestDto, @PathVariable int id) {
        return jobApplicationService.updateJobApplication(jobApplicationRequestDto, id);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public void deleteJobApplicationById(@PathVariable int id) {
        jobApplicationService.deleteJobApplicationById(id);
    }
}
