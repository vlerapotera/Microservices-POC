package com.microservices.profileservice.repository;

import com.microservices.profileservice.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProfileRepo extends MongoRepository <Profile, String>{
}
