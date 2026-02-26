package com.microservices.joblisting.controller;

import com.microservices.joblisting.model.JobListing;
import com.microservices.joblisting.service.JobListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/jobs")
public class JobListingController {

    private final JobListingService jobListingService;

    @GetMapping("/all")
    public String getAllJobs(Model model){
        List<JobListing> jobs=jobListingService.getAllJobs();
        model.addAttribute("jobs",jobs);
        return "alljobs";
    }
    @PostMapping
    public String getJobDescription(@RequestParam("jobId") String jobId, Model model) {
            Optional<JobListing> job = jobListingService.getJobById(jobId);
            job.ifPresent(jobListing -> model.addAttribute("job", jobListing));
                return "jobdescription";
    }
}
