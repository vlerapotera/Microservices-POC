package com.microservices.joblisting.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(value="joblistings")
public class JobListing {

    @Id
    private String id;
    private String title;
    private String jobType;
    private String company;
    private String address;
    private String shortDesc;
    private String longDesc;
}
