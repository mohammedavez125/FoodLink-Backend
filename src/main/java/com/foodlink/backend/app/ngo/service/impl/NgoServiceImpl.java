package com.foodlink.backend.app.ngo.service.impl;

import com.foodlink.backend.app.ngo.dto.req.UpdateNgoProfileRequest;
import com.foodlink.backend.app.ngo.dto.res.NgoProfileResponse;
import com.foodlink.backend.app.ngo.model.NgoProfile;
import com.foodlink.backend.app.ngo.repository.NgoProfileRepository;
import com.foodlink.backend.app.ngo.service.NgoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class NgoServiceImpl implements NgoService {

    private final NgoProfileRepository ngoProfileRepository;


    @Override
    public NgoProfileResponse getMyProfile(
            String userId
    ) {

        NgoProfile profile = ngoProfileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "NGO profile not found"));

        return mapToResponse(profile);
    }

    @Override
    public NgoProfileResponse updateProfile(
            String userId,
            UpdateNgoProfileRequest request
    ) {

        NgoProfile profile = ngoProfileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "NGO profile not found"));

        profile.setNgoName(request.getNgoName());
        profile.setRegistrationNumber(
                request.getRegistrationNumber());
        profile.setContactPerson(
                request.getContactPerson());
        profile.setPhone(request.getPhone());
        profile.setAddress(request.getAddress());
        profile.setDescription(
                request.getDescription());
        profile.setActive(request.isActive());
        profile.setUpdatedAt(Instant.now());

        return mapToResponse(
                ngoProfileRepository.save(profile)
        );
    }

    @Override
    public void deleteProfile(
            String userId
    ) {

        NgoProfile profile = ngoProfileRepository
                .findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "NGO profile not found"));

        ngoProfileRepository.delete(profile);
    }

    private NgoProfileResponse mapToResponse(
            NgoProfile profile
    ) {

        return NgoProfileResponse.builder()
                .id(profile.getId())
                .userId(profile.getUserId())
                .ngoName(profile.getNgoName())
                .registrationNumber(
                        profile.getRegistrationNumber())
                .contactPerson(profile.getContactPerson())
                .phone(profile.getPhone())
                .address(profile.getAddress())
                .description(profile.getDescription())
                .active(profile.isActive())
                .build();
    }
}