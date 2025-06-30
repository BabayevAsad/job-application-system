package com.AsadBabayev.dto.company;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyRequestDto {

    @NotBlank(message = "Company name is required.")
    @Size(min = 2, max = 100, message = "Company name must be between 2 and 100 characters.")
    private String name;

    @NotBlank(message = "Company description is required.")
    @Size(max = 500, message = "Description must be at most 500 characters.")
    private String description;

    @NotBlank(message = "Location is required.")
    private String location;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "\\+?[0-9\\-]{7,15}", message = "Invalid phone number.")
    private String phone;
}
