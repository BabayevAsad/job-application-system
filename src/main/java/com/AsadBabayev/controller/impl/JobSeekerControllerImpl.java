package com.AsadBabayev.controller.impl;

import com.AsadBabayev.controller.JobSeekerController;
import com.AsadBabayev.dto.JobSeeker.JobSeekerDto;
import com.AsadBabayev.dto.JobSeeker.JobSeekerRequestDto;
import com.AsadBabayev.services.JobSeekerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest/api/jobSeeker")
public class JobSeekerControllerImpl implements JobSeekerController {

    private final JobSeekerService jobSeekerService;

    public JobSeekerControllerImpl(JobSeekerService jobSeekerService) {
        this.jobSeekerService = jobSeekerService;
    }

    @GetMapping("/getAll")
    @Override
    public List<JobSeekerDto> getAllJobSeeker() {
        return jobSeekerService.getAllJobSeeker();
    }

    @GetMapping("/getById/{id}")
    @Override
    public Optional<JobSeekerDto> getJobSeekerById(@PathVariable int id) {
        return Optional.ofNullable(jobSeekerService.getJobSeekerById(id));
    }

    @PostMapping("/save")
    @Override
    public JobSeekerDto saveJobSeeker(@RequestBody @Valid JobSeekerRequestDto jobSeekerRequestDto) {
        return jobSeekerService.saveJobSeeker(jobSeekerRequestDto);
    }

    @PutMapping("/update/{id}")
    @Override
    public JobSeekerDto updateJobSeeker(@PathVariable int id, @RequestBody @Valid JobSeekerRequestDto jobSeekerRequestDto) {
        return jobSeekerService.updateJobSeeker(id, jobSeekerRequestDto);
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public void deleteJobSeeker(@PathVariable int id) {
        jobSeekerService.deleteJobSeekerById(id);
    }
}
