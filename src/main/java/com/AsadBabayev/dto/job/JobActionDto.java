package com.AsadBabayev.dto.job;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobActionDto {
    private Integer id;

    private String title;

    private String description;

    private String requirements;

    private String location;

    private String salaryRange;

    private boolean active;
}
