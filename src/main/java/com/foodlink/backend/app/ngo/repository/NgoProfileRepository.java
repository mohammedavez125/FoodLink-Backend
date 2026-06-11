package com.foodlink.backend.app.ngo.repository;

import com.foodlink.backend.app.ngo.model.NgoProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NgoProfileRepository
        extends MongoRepository<NgoProfile, String> {

    Optional<NgoProfile> findByUserId(String userId);

    boolean existsByUserId(String userId);

    boolean existsByRegistrationNumber(String registrationNumber);
}