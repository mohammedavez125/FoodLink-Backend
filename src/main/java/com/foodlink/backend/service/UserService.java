package com.foodlink.backend.service;

import com.foodlink.backend.model.RegisterUserRequest;
import com.foodlink.backend.model.RegisterUserResponse;
import com.foodlink.backend.model.Role;
import com.foodlink.backend.model.User;
import com.foodlink.backend.repository.RoleRepository;
import com.foodlink.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    public RegisterUserResponse registerUser(RegisterUserRequest registerUserRequest) {
        // Check username and email uniqueness
        if (userRepository.findByUsername(registerUserRequest.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByEmail(registerUserRequest.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        // Fetching USER role from MongoDB
        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("USER role not found"));

        User user = new User();

        user.setEmail(registerUserRequest.getEmail());
        user.setUsername(registerUserRequest.getUsername());
        user.setFirstname(registerUserRequest.getUsername());
        user.setLastname(registerUserRequest.getLastname());
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        user.setRole(userRole);
        user.setPhone(registerUserRequest.getPhone());
        user.setAddress(registerUserRequest.getAddress());
        User saved = userRepository.save(user);
        return new RegisterUserResponse(saved.getUsername(),saved.getRole());
    }

}
