package com.foodlink.backend.security;

import com.foodlink.backend.security.dto.request.AuthRequestDto;
import com.foodlink.backend.security.dto.request.RegisterDonorRequestDto;
import com.foodlink.backend.security.dto.request.RegisterNgoRequestDto;
import com.foodlink.backend.security.dto.response.LoginResponseDto;
import com.foodlink.backend.security.dto.response.RegisterUserResponseDto;
import com.foodlink.backend.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody AuthRequestDto request
    ) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    @PostMapping("/signup/donor")
    public ResponseEntity<RegisterUserResponseDto> signupDonor(
            @Valid @RequestBody RegisterDonorRequestDto request
    ) {

        return ResponseEntity.ok(
                authService.registerDonor(request)
        );
    }

    @PostMapping("/signup/ngo")
    public ResponseEntity<RegisterUserResponseDto> signupNgo(
            @Valid @RequestBody RegisterNgoRequestDto request
    ) {

        return ResponseEntity.ok(
                authService.registerNgo(request)
        );
    }
}