package com.foodlink.backend.security.dto.request;

import com.foodlink.backend.app.donor.dto.req.CreateDonorProfileRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterDonorRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    @Email
    private String email;

    @NotBlank
    private String password;

    @Valid
    private CreateDonorProfileRequest donorProfile;
}