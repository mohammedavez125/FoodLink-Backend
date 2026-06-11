package com.foodlink.backend.security.dto.request;

import com.foodlink.backend.app.ngo.dto.req.CreateNgoProfileRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterNgoRequestDto {

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
    private CreateNgoProfileRequest ngoProfile;
}