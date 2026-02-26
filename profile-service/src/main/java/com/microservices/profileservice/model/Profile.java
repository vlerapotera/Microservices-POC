package com.microservices.profileservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(value="profiles")
public class Profile {
    @Id
    private String id;

    private List<Experience> experiences = new ArrayList<>();

    private List<Education> educations = new ArrayList<>();
    private String skills;
    private String cvPath;

    public List<Education> getEducation() {
        return educations;
    }

    public void setEducation(List<Education> education) {
        this.educations = education;
    }

    public List<Experience> getExperiences() {
        return experiences;
    }

    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }
}

