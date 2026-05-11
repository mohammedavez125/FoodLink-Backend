package com.foodlink.backend.controller;

import com.foodlink.backend.model.*;
import com.foodlink.backend.repository.UserRepository;
import com.foodlink.backend.service.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@RestController
public class UserController {

    private final UserRepository repo;
    private final UserService userService;

    //  GET ALL USERS
    @GetMapping("/get-users")
    @PreAuthorize("hasAuthority('ADMIN_ACCESS')")
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    //  REGISTER A NEW USER
    @PostMapping("/register-user")
    public ResponseEntity<RegisterUserResponse> createUser(@RequestBody RegisterUserRequest request) {
        RegisterUserResponse res = userService.registerUser(request);
        return ResponseEntity.ok(res);
    }

    @PreAuthorize("hasAuthority('ADMIN_ACCESS')")
    @GetMapping("/get-user/{username}")
    public User getUser(@PathVariable String username) {

        return repo.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

}
