package com.foodlink.backend.security.dto.request;

import com.foodlink.backend.model.Address;
import lombok.Data;

@Data
public class RegisterUserRequestDto {
    private String email;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String phone;
    private Address address;
}
