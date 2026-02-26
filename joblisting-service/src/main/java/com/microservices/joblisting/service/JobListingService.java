package com.microservices.joblisting.service;

import com.microservices.joblisting.model.JobListing;
import com.microservices.joblisting.repository.JobListingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class JobListingService {
    //private static final Logger logger = LoggerFactory.getLogger(JobListingService.class);
    private final JobListingRepository jobListingRepository;
    public List<JobListing> getAllJobs() {
        List<JobListing> jobs = jobListingRepository.findAll();
        //logger.info("Retrieved jobs: {}", jobs);
        return jobs;
    }
    public Optional<JobListing> getJobById(String id) {
        Optional<JobListing> job = jobListingRepository.findById(id);
        //logger.info("Retrieved job: {}", job);
        return job;
    }
}

