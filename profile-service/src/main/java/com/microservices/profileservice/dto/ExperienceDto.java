package com.microservices.profileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDto {
    private String role;
    private String companyName;
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private String startDate;
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private String endDate;

    private String description;
}
