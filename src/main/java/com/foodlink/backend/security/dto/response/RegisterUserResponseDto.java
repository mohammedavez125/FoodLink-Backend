package com.foodlink.backend.security.dto.response;

import com.foodlink.backend.model.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterUserResponseDto {
    private String username;
    private Role role;
}
