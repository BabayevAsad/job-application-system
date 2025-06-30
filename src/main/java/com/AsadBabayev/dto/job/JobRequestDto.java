package com.AsadBabayev.dto.job;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobRequestDto {

    @NotBlank(message = "Job title is required.")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters.")
    private String title;

    @NotBlank(message = "Job description is required.")
    @Size(min = 10, message = "Description must be at least 10 characters.")
    private String description;

    @NotBlank(message = "Job requirements are required.")
    @Size(min = 5, message = "Requirements must be at least 5 characters.")
    private String requirements;

    @NotBlank(message = "Location is required.")
    private String location;

    @NotBlank(message = "Salary range are required.")
    private String salaryRange;

    @NotNull(message = "Active must entered.")
    private boolean active;

    @NotNull(message = "Company ID is required.")
    private Integer companyId;
}
