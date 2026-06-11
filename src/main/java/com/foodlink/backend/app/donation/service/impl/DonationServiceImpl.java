package com.foodlink.backend.app.donation.service.impl;

import com.foodlink.backend.app.donation.dto.req.AcceptDonationRequest;
import com.foodlink.backend.app.donation.dto.req.CreateDonationRequest;
import com.foodlink.backend.app.donation.dto.req.UpdateDonationRequest;
import com.foodlink.backend.app.donation.dto.res.DonationResponse;
import com.foodlink.backend.app.donation.model.Donation;
import com.foodlink.backend.app.donation.model.DonationStatus;
import com.foodlink.backend.app.donation.repository.DonationRepository;
import com.foodlink.backend.app.donation.service.DonationService;
import com.foodlink.backend.app.donor.model.DonorProfile;
import com.foodlink.backend.app.donor.repository.DonorProfileRepository;
import com.foodlink.backend.app.ngo.model.NgoProfile;
import com.foodlink.backend.app.ngo.repository.NgoProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationService {
    private final DonationRepository donationRepository;
    private final DonorProfileRepository donorProfileRepository;
    private final NgoProfileRepository ngoProfileRepository;

    @Override
    public DonationResponse createDonation(
            String userId,
            CreateDonationRequest request
    ) {

        DonorProfile donorProfile =
                donorProfileRepository
                        .findByUserId(userId)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Donor profile not found"
                                )
                        );

        Donation donation =
                Donation.builder()
                        .donorProfileId(
                                donorProfile.getId()
                        )
                        .foodName(
                                request.getFoodName()
                        )
                        .category(
                                request.getCategory()
                        )
                        .quantity(
                                request.getQuantity()
                        )
                        .description(
                                request.getDescription()
                        )
                        .pickupLocation(
                                request.getPickupLocation()
                        )
                        .expiryTime(
                                request.getExpiryTime()
                        )
                        .status(
                                DonationStatus.AVAILABLE
                        )
                        .createdAt(
                                Instant.now()
                        )
                        .updatedAt(
                                Instant.now()
                        )
                        .build();

        return mapToResponse(
                donationRepository.save(
                        donation
                )
        );
    }

    @Override
    public DonationResponse updateDonation(
            String donationId,
            String userId,
            UpdateDonationRequest request
    ) {

        DonorProfile donorProfile =
                getDonorProfile(userId);

        Donation donation =
                getDonation(donationId);

        validateDonorOwner(
                donation,
                donorProfile
        );

        updateAvailableDonationDetails(
                donation,
                request
        );

        donation.setUpdatedAt(
                Instant.now()
        );

        return mapToResponse(
                donationRepository.save(
                        donation
                )
        );
    }

    private void updateAvailableDonationDetails(
            Donation donation,
            UpdateDonationRequest request
    ) {

        if (donation.getStatus()
                != DonationStatus.AVAILABLE) {

            throw new RuntimeException(
                    "Only available donations can be updated"
            );
        }

        if (request.getExpiryTime() != null
                && !request.getExpiryTime().isAfter(
                Instant.now()
        )) {

            throw new RuntimeException(
                    "Expiry time must be in the future"
            );
        }

        donation.setFoodName(
                request.getFoodName()
        );

        donation.setCategory(
                request.getCategory()
        );

        donation.setQuantity(
                request.getQuantity()
        );

        donation.setDescription(
                request.getDescription()
        );

        donation.setPickupLocation(
                request.getPickupLocation()
        );

        donation.setExpiryTime(
                request.getExpiryTime()
        );
    }

    @Override
    public DonationResponse acceptDonation(
            String donationId,
            String userId,
            AcceptDonationRequest request
    ) {

        NgoProfile ngoProfile =
                getNgoProfile(userId);

        Donation donation =
                getDonation(donationId);

        if (donation.getStatus()
                != DonationStatus.AVAILABLE) {

            throw new RuntimeException(
                    "Donation already accepted"
            );
        }

        if (donation.getExpiryTime()
                .isBefore(
                        Instant.now()
                )) {

            throw new RuntimeException(
                    "Donation expired"
            );
        }

        donation.setAcceptedNgoProfileId(
                ngoProfile.getId()
        );

        donation.setDropLocation(
                request.getDropLocation()
        );

        donation.setStatus(
                DonationStatus.ACCEPTED
        );

        donation.setAcceptedAt(
                Instant.now()
        );

        donation.setUpdatedAt(
                Instant.now()
        );

        return mapToResponse(
                donationRepository.save(
                        donation
                )
        );
    }

    @Override
    public DonationResponse dispatchDonation(
            String donationId,
            String userId
    ) {

        DonorProfile donorProfile =
                getDonorProfile(userId);

        Donation donation =
                getDonation(donationId);

        validateDonorOwner(
                donation,
                donorProfile
        );

        if (donation.getStatus()
                != DonationStatus.ACCEPTED) {

            throw new RuntimeException(
                    "Only accepted donations can be dispatched"
            );
        }

        donation.setStatus(
                DonationStatus.DISPATCHED
        );

        donation.setDispatchedAt(
                Instant.now()
        );

        donation.setUpdatedAt(
                Instant.now()
        );

        return mapToResponse(
                donationRepository.save(
                        donation
                )
        );
    }

    @Override
    public DonationResponse receiveDonation(
            String donationId,
            String userId
    ) {

        NgoProfile ngoProfile =
                getNgoProfile(userId);

        Donation donation =
                getDonation(donationId);

        validateAcceptedNgo(
                donation,
                ngoProfile
        );

        if (donation.getStatus()
                != DonationStatus.DISPATCHED) {

            throw new RuntimeException(
                    "Only dispatched donations can be received"
            );
        }

        donation.setStatus(
                DonationStatus.RECEIVED
        );

        donation.setReceivedAt(
                Instant.now()
        );

        donation.setUpdatedAt(
                Instant.now()
        );

        return mapToResponse(
                donationRepository.save(
                        donation
                )
        );
    }

    @Override
    public DonationResponse completeDonation(
            String donationId,
            String userId
    ) {

        NgoProfile ngoProfile =
                getNgoProfile(userId);

        Donation donation =
                getDonation(donationId);

        validateAcceptedNgo(
                donation,
                ngoProfile
        );

        if (donation.getStatus()
                != DonationStatus.RECEIVED) {

            throw new RuntimeException(
                    "Only received donations can be completed"
            );
        }

        donation.setStatus(
                DonationStatus.COMPLETED
        );

        donation.setCompletedAt(
                Instant.now()
        );

        donation.setUpdatedAt(
                Instant.now()
        );

        return mapToResponse(
                donationRepository.save(
                        donation
                )
        );
    }

    @Override
    public void deleteDonation(
            String donationId,
            String userId
    ) {

        DonorProfile donorProfile =
                getDonorProfile(userId);

        Donation donation =
                getDonation(donationId);

        validateDonorOwner(
                donation,
                donorProfile
        );

        if (donation.getStatus()
                != DonationStatus.AVAILABLE) {

            throw new RuntimeException(
                    "Accepted donations cannot be deleted"
            );
        }

        donationRepository.delete(
                donation
        );
    }

    @Override
    public List<DonationResponse>
    getAvailableDonations() {

        return donationRepository
                .findByStatusAndExpiryTimeAfter(
                        DonationStatus.AVAILABLE,
                        Instant.now()
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<DonationResponse>
    getMyDonations(
            String userId
    ) {

        DonorProfile donorProfile =
                getDonorProfile(userId);

        return donationRepository
                .findByDonorProfileId(
                        donorProfile.getId()
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<DonationResponse> getAcceptedDonations(
            String userId
    ) {

        NgoProfile ngoProfile =
                getNgoProfile(userId);

        return donationRepository
                .findByAcceptedNgoProfileId(
                        ngoProfile.getId()
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<DonationResponse> getDonorHistory(
            String userId
    ) {

        DonorProfile donorProfile =
                getDonorProfile(userId);

        return getDonorHistoryByStatus(
                donorProfile.getId(),
                DonationStatus.COMPLETED
        );
    }

    @Override
    public List<DonationResponse> getNgoHistory(
            String userId
    ) {

        NgoProfile ngoProfile =
                getNgoProfile(userId);

        return getNgoHistoryByStatus(
                ngoProfile.getId(),
                DonationStatus.COMPLETED
        );
    }

    private List<DonationResponse> getDonorHistoryByStatus(
            String donorProfileId,
            DonationStatus status
    ) {

        return donationRepository
                .findByDonorProfileIdAndStatus(
                        donorProfileId,
                        status
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private List<DonationResponse> getNgoHistoryByStatus(
            String ngoProfileId,
            DonationStatus status
    ) {

        return donationRepository
                .findByAcceptedNgoProfileIdAndStatus(
                        ngoProfileId,
                        status
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private Donation getDonation(
            String donationId
    ) {

        return donationRepository
                .findById(donationId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Donation not found"
                        )
                );
    }

    private DonorProfile getDonorProfile(
            String userId
    ) {

        return donorProfileRepository
                .findByUserId(userId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Donor profile not found"
                        )
                );
    }

    private NgoProfile getNgoProfile(
            String userId
    ) {

        return ngoProfileRepository
                .findByUserId(userId)
                .orElseThrow(
                        () -> new RuntimeException(
                                "NGO profile not found"
                        )
                );
    }

    private void validateDonorOwner(
            Donation donation,
            DonorProfile donorProfile
    ) {

        if (!donation.getDonorProfileId()
                .equals(donorProfile.getId())) {

            throw new RuntimeException(
                    "Not owner of donation"
            );
        }
    }

    private void validateAcceptedNgo(
            Donation donation,
            NgoProfile ngoProfile
    ) {

        if (donation.getAcceptedNgoProfileId() == null
                || !donation.getAcceptedNgoProfileId()
                .equals(ngoProfile.getId())) {

            throw new RuntimeException(
                    "Not accepted NGO for donation"
            );
        }
    }

    private DonationResponse mapToResponse(
            Donation donation
    ) {

        return DonationResponse.builder()
                .id(donation.getId())
                .donorProfileId(
                        donation.getDonorProfileId()
                )
                .acceptedNgoProfileId(
                        donation.getAcceptedNgoProfileId()
                )
                .foodName(
                        donation.getFoodName()
                )
                .category(
                        donation.getCategory()
                )
                .quantity(
                        donation.getQuantity()
                )
                .description(
                        donation.getDescription()
                )
                .pickupLocation(
                        donation.getPickupLocation()
                )
                .dropLocation(
                        donation.getDropLocation()
                )
                .status(
                        donation.getStatus()
                )
                .estimatedMinutes(
                        donation.getEstimatedMinutes()
                )
                .expiryTime(
                        donation.getExpiryTime()
                )
                .acceptedAt(
                        donation.getAcceptedAt()
                )
                .dispatchedAt(
                        donation.getDispatchedAt()
                )
                .receivedAt(
                        donation.getReceivedAt()
                )
                .completedAt(
                        donation.getCompletedAt()
                )
                .build();
    }

}
