package com.AsadBabayev.dto.job;

import com.AsadBabayev.dto.company.CompanyActionDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "title", "description", "requirements", "location", "salaryRange", "active", "dtoCompany"})
public class JobDto {

    private Integer id;

    private String title;

    private String description;

    private String requirements;

    private String location;

    private String salaryRange;

    private boolean active;

    @JsonProperty("company")
    private CompanyActionDto companyActionDto;
}
