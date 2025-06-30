package com.AsadBabayev.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "job_seeker")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobSeeker extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonProperty("firstName")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @JsonProperty("lastName")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_Date", nullable = false)
    private LocalDate birthDate;

    @JsonProperty("nationality")
    @Column(name = "nationality")
    private String nationality;// change it in other classes

    @Email
    @JsonProperty("email")
    @Column(name = "email", nullable = false)
    private String email;

    @JsonProperty("phone")
    @Column(name = "phone", nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    @JsonProperty("gender")
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @JsonProperty("country")
    @Column(name = "country", nullable = false)
    private String country;

    @JsonProperty("city")
    @Column(name = "city", nullable = false)
    private String city;

    @JsonProperty("address")
    @Column(name = "address")
    private String address;

    @JsonProperty("postalCode")
    @Column(name = "postal_code")
    private String postalCode;
}
