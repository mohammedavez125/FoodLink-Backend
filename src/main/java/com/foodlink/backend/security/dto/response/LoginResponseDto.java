package com.foodlink.backend.security.dto.response;

import com.foodlink.backend.model.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private String token;
    private String username;
    private String email;
    private Role role;
}