package com.AsadBabayev.dto.JobSeeker;

import com.AsadBabayev.entities.Gender;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobSeekerRequestDto {

    @NotBlank(message = "First name cannot be null. Please enter your first name.")
    @Size(min = 3, max = 20, message = "First name must be between 3 and 20 characters.")
    private String firstName;

    @NotBlank(message = "Last name cannot be null. Please enter your  lastname.")
    @Size(min = 3, max = 20, message = "Enter more then 3 and less then 20 characters.")
    private String lastName;

    @NotNull(message = "Birth date is required.")
    private LocalDate birthDate;

    @NotBlank(message = "Nationality is required.")
    private String nationality;

    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required.")
    @Pattern(regexp = "\\+?[0-9\\-]{7,15}")
    private String phone;

    @NotNull(message = "Gender is required.")
    private Gender gender;

    @NotBlank(message = "Country is required.")
    private String country;

    @NotBlank(message = "City is required.")
    private String city;

    private String address;

    private String postalCode;
}
