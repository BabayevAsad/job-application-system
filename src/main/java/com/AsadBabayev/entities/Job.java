package com.AsadBabayev.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "job")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonProperty("title")
    @Column(name = "title", nullable = false)
    private String title;

    @JsonProperty("description")
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @JsonProperty("requirements")
    @Column(name = "requirements", nullable = false, columnDefinition = "TEXT")
    private String requirements;

    @JsonProperty("location")
    @Column(name = "location", nullable = false)
    private String location;

    @JsonProperty("salaryRange")
    @Column(name = "salary_range")
    private String salaryRange;

    @JsonProperty("active")
    @Column(name = "is_active", nullable = false)
    private boolean active;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(optional = false)
    private Company company;
}
