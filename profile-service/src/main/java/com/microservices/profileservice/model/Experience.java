package com.microservices.profileservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Experience {
    private String role;
    private String companyName;
    private String startDate;
    private String endDate;
    private String description;
}
