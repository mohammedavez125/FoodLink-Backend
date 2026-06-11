package com.foodlink.backend.app.donation.repository;

import com.foodlink.backend.app.donation.model.Donation;
import com.foodlink.backend.app.donation.model.DonationStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface DonationRepository
        extends MongoRepository<Donation, String> {

    List<Donation> findByStatusAndExpiryTimeAfter(
            DonationStatus status,
            Instant now
    );

    List<Donation> findByDonorProfileId(
            String donorProfileId
    );

    List<Donation> findByDonorProfileIdAndStatus(
            String donorProfileId,
            DonationStatus status
    );

    List<Donation> findByAcceptedNgoProfileId(
            String ngoProfileId
    );

    List<Donation> findByAcceptedNgoProfileIdAndStatus(
            String ngoProfileId,
            DonationStatus status
    );
}
