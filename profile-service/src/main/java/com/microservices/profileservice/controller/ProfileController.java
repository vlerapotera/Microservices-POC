package com.microservices.profileservice.controller;

import com.microservices.profileservice.model.Education;
import com.microservices.profileservice.model.Experience;
import com.microservices.profileservice.model.Profile;
import com.microservices.profileservice.service.ProfileService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public String viewProfile(HttpSession session, Model model) {
        String userName = (String) session.getAttribute("name");
        model.addAttribute("name", userName);
        return "editprofile";

    }

    @PostMapping
    public ModelAndView saveProfile(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("skills") String skills,
            @RequestParam("experienceRole") String[] experienceRoles,
            @RequestParam("experienceCompanyName") String[] experienceCompanyNames,
            @RequestParam("experienceStartDate") String[] experienceStartDates,
            @RequestParam("experienceEndDate") String[] experienceEndDates,
            @RequestParam("experienceDescription") String[] experienceDescriptions,
            @RequestParam("educationLevel") String[] educationLevels,
            @RequestParam("educationInstitutionName") String[] educationInstitutionNames,
            @RequestParam("educationGraduationDate") String[] educationGraduationDates
    ) {
        // Parse form data and create Experience and Education objects
        List<Experience> experiences = new ArrayList<>();
        for (int i = 0; i < experienceRoles.length; i++) {
            experiences.add(Experience.builder()
                    .role(experienceRoles[i])
                    .companyName(experienceCompanyNames[i])
                    .startDate(experienceStartDates[i]) // Pass the date string directly
                    .endDate(experienceEndDates[i])
                    .description(experienceDescriptions[i])
                    .build());
        }

        List<Education> educations = new ArrayList<>();
        for (int i = 0; i < educationLevels.length; i++) {
            educations.add(Education.builder()
                    .level(educationLevels[i])
                    .institutionName(educationInstitutionNames[i])
                    .graduationDate(educationGraduationDates[i])
                    .build());
        }

        Profile profile = new Profile();
        profile.setSkills(skills);

        // Save the profile
        Profile savedProfile = profileService.saveProfile(profile);

        // Use the saved profile's ID to add experiences and education
        String profileId = savedProfile.getId();
        for (Experience experience : experiences) {
            profileService.addExperience(profileId, experience);
        }
        for (Education education : educations) {
            profileService.addEducation(profileId, education);
        }

        return new ModelAndView("redirect:http://localhost:8080/home");
    }

}



