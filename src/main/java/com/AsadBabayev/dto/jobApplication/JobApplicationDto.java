package com.AsadBabayev.dto.jobApplication;

import com.AsadBabayev.dto.JobSeeker.JobSeekerDto;
import com.AsadBabayev.dto.job.JobDto;
import com.AsadBabayev.entities.ApplicationStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationDto {

    private Integer id;

    private ApplicationStatus status;

    private String coverLetter;

    @JsonProperty("jobSeeker")
    private JobSeekerDto jobSeekerDto;

    @JsonProperty("job")
    private JobDto jobDto;
}
