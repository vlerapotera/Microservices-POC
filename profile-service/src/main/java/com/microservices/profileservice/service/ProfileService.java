package com.microservices.profileservice.service;

import com.microservices.profileservice.dto.EducationDto;
import com.microservices.profileservice.dto.ExperienceDto;
import com.microservices.profileservice.model.Education;
import com.microservices.profileservice.model.Experience;
import com.microservices.profileservice.model.Profile;
import com.microservices.profileservice.repository.ProfileRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProfileService {
    private final ProfileRepo profileRepository;
    // Retrieve Education object by index within a Profile
    public Education getEducation(String profileId, int educationIndex) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
        return profile.getEducation().get(educationIndex);
    }

    // Retrieve Experience object by index within a Profile
    public Experience getExperience(String profileId, int experienceIndex) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
        return profile.getExperiences().get(experienceIndex);
    }


    public void addExperience(String profileId, Experience experience) {
        Profile profile = profileRepository.findById(profileId).orElseGet(() -> {
            Profile newProfile = new Profile();
            newProfile.setId(profileId);
            return newProfile;
        });

        // Add experience to the profile
        profile.getExperiences().add(experience);

        // Save the profile
        profileRepository.save(profile);
    }
    public Profile saveProfile(Profile profile) {
        return profileRepository.save(profile);
    }


    public Profile updateExperience(String profileId, int experienceId, Experience experience) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
        profile.getExperiences().set(experienceId, experience);
        return profileRepository.save(profile);
    }

    public Profile deleteExperience(String profileId, int experienceId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
        profile.getExperiences().remove(experienceId);
        return profileRepository.save(profile);
    }

    // Education CRUD
    public void addEducation(String profileId, Education education) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
        profile.getEducation().add(education);
        profileRepository.save(profile);
    }

    public Profile updateEducation(String profileId, int educationId, Education education) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
        profile.getEducation().set(educationId, education);
        return profileRepository.save(profile);
    }

    public Profile deleteEducation(String profileId, int educationId) {
        Profile profile = profileRepository.findById(profileId).orElseThrow(() -> new RuntimeException("Profile not found"));
        profile.getEducation().remove(educationId);
        return profileRepository.save(profile);
    }

    public ExperienceDto convertToExperienceDTO(Experience experience) {
        return ExperienceDto.builder()
                .role(experience.getRole())
                .companyName(experience.getCompanyName())
                .startDate(experience.getStartDate())
                .endDate(experience.getEndDate())
                .description(experience.getDescription())
                .build();
    }

    public Experience convertToExperience(ExperienceDto experienceDTO) {
        return Experience.builder()
                .role(experienceDTO.getRole())
                .companyName(experienceDTO.getCompanyName())
                .startDate(experienceDTO.getStartDate())
                .endDate(experienceDTO.getEndDate())
                .description(experienceDTO.getDescription())
                .build();
    }

    public EducationDto convertToEducationDTO(Education education) {
        return EducationDto.builder()
                .level(education.getLevel())
                .institutionName(education.getInstitutionName())
                .graduationDate(education.getGraduationDate())
                .build();
    }

    public Education convertToEducation(EducationDto educationDTO) {
        return Education.builder()
                .level(educationDTO.getLevel())
                .institutionName(educationDTO.getInstitutionName())
                .graduationDate(educationDTO.getGraduationDate())
                .build();
    }
}

