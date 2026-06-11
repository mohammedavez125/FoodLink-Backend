package com.foodlink.backend.app.ngo.service;

import com.foodlink.backend.app.ngo.dto.req.CreateNgoProfileRequest;
import com.foodlink.backend.app.ngo.dto.req.UpdateNgoProfileRequest;
import com.foodlink.backend.app.ngo.dto.res.NgoProfileResponse;
import org.springframework.stereotype.Service;

@Service
public interface NgoService {


    NgoProfileResponse getMyProfile(
            String userId
    );

    NgoProfileResponse updateProfile(
            String userId,
            UpdateNgoProfileRequest request
    );

    void deleteProfile(
            String userId
    );
}