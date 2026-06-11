package com.foodlink.backend.app.donor.service.impl;

import com.foodlink.backend.app.donor.dto.req.UpdateDonorProfileRequest;
import com.foodlink.backend.app.donor.dto.res.DonorProfileResponse;
import com.foodlink.backend.app.donor.model.DonorProfile;
import com.foodlink.backend.app.donor.repository.DonorProfileRepository;
import com.foodlink.backend.app.donor.service.DonorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class DonorServiceImpl implements DonorService {

    private final DonorProfileRepository donorProfileRepository;


    @Override
    public DonorProfileResponse getMyProfile(String userId) {

        DonorProfile profile = donorProfileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Donor profile not found"));

        return mapToResponse(profile);
    }

    @Override
    public DonorProfileResponse updateProfile(
            String userId,
            UpdateDonorProfileRequest request
    ) {

        DonorProfile profile = donorProfileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Donor profile not found"));

        profile.setOrganizationName(
                request.getOrganizationName());

        profile.setContactPerson(
                request.getContactPerson());

        profile.setPhone(
                request.getPhone());

        profile.setAddress(
                request.getAddress());

        profile.setDescription(
                request.getDescription());

        profile.setActive(
                request.isActive());

        profile.setUpdatedAt(
                Instant.now());

        return mapToResponse(
                donorProfileRepository.save(profile)
        );
    }

    @Override
    public void deleteProfile(String userId) {

        DonorProfile profile = donorProfileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Donor profile not found"));

        donorProfileRepository.delete(profile);
    }

    private DonorProfileResponse mapToResponse(
            DonorProfile profile
    ) {

        return DonorProfileResponse.builder()
                .id(profile.getId())
                .userId(profile.getUserId())
                .donorType(profile.getDonorType())
                .organizationName(profile.getOrganizationName())
                .contactPerson(profile.getContactPerson())
                .phone(profile.getPhone())
                .address(profile.getAddress())
                .description(profile.getDescription())
                .active(profile.isActive())
                .build();
    }
}