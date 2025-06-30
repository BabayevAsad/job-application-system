package com.AsadBabayev.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @JsonProperty("location")
    @Column(name = "location", nullable = false)
    private String location;

    @Email(message = "Invalid email format")
    @Column(length = 25, nullable = false)
    private String email;

    @Pattern(regexp = "\\+?[0-9\\-]{7,15}", message = "Invalid phone number")
    @Column(length = 20, nullable = false)
    private String phone;
}
