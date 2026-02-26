package com.microservices.joblisting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobListingDto {
    private int id;
    private String title;
    private String jobType;
    private String company;
    private String address;
    private String shortDesc;
    private String longDesc;
}
