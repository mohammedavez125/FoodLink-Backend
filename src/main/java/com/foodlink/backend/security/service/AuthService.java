package com.foodlink.backend.security.service;

import com.foodlink.backend.security.dto.request.AuthRequestDto;
import com.foodlink.backend.security.dto.request.RegisterDonorRequestDto;
import com.foodlink.backend.security.dto.request.RegisterNgoRequestDto;
import com.foodlink.backend.security.dto.response.LoginResponseDto;
import com.foodlink.backend.security.dto.response.RegisterUserResponseDto;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface AuthService {

    RegisterUserResponseDto registerDonor(
            RegisterDonorRequestDto request
    );

    RegisterUserResponseDto registerNgo(
            RegisterNgoRequestDto request
    );

    LoginResponseDto login(
            AuthRequestDto authReq
    );

    LoginResponseDto handleOauth2LoginRequest(
            OAuth2User oAuth2User
    );
}