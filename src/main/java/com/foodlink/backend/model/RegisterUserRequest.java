package com.foodlink.backend.model;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String email;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String phone;
    private String address;
}
