package com.microservices.profileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private String name;
    private String email;
    private String cvPath;
    private List<ExperienceDto> experiences;
    private List<EducationDto> education;
}

