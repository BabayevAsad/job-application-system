package com.AsadBabayev.dto.JobSeeker;

import com.AsadBabayev.entities.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobSeekerDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

    private String nationality;

    private Gender gender;

    private String country;

    private String city;
}
