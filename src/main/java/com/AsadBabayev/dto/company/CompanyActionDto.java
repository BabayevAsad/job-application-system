package com.AsadBabayev.dto.company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyActionDto {

    private Integer id;

    private String name;

    private String description;

    private String location;

    private String email;

    private String phone;
}
