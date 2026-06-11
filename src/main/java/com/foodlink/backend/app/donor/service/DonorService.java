package com.foodlink.backend.app.donor.service;


import com.foodlink.backend.app.donor.dto.req.CreateDonorProfileRequest;
import com.foodlink.backend.app.donor.dto.req.UpdateDonorProfileRequest;
import com.foodlink.backend.app.donor.dto.res.DonorProfileResponse;
import org.springframework.stereotype.Service;

@Service
public interface DonorService {


    DonorProfileResponse getMyProfile(String userId);

    DonorProfileResponse updateProfile(
            String userId,
            UpdateDonorProfileRequest request
    );

    void deleteProfile(String userId);
}
