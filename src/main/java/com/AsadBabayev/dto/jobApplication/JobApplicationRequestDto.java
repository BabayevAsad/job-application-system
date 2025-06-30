package com.AsadBabayev.dto.jobApplication;

import com.AsadBabayev.entities.ApplicationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationRequestDto {

    @NotNull(message = "Job ID is required.")
    private Integer jobId;

    @NotNull(message = "Job seeker ID is required.")
    private Integer jobSeekerId;

    @NotNull(message = "Status is required.")
    private ApplicationStatus status;

    @NotBlank(message = "Cover letter is required.")
    @Size(max = 5000, message = "Cover letter must be less than 5000 characters.")
    private String coverLetter;
}
