package com.microservices.joblisting.repository;

import com.microservices.joblisting.model.JobListing;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JobListingRepository extends MongoRepository<JobListing, String> {
}
