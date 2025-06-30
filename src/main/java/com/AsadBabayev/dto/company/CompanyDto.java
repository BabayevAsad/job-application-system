package com.AsadBabayev.dto.company;

import com.AsadBabayev.dto.job.JobActionDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto {

    private Integer id;

    private String name;

    private String description;

    private String location;

    private String email;

    private String phone;

    @JsonProperty("jobs")
    @Builder.Default
    private List<JobActionDto> jobActionDtoList = new ArrayList<>();
}
