package com.foodlink.backend.controller;

import com.foodlink.backend.repository.UserRepository;
import com.foodlink.backend.model.User;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository repo;

    @GetMapping("/get-users")
    public List<User> getAllUsers(){
        return repo.findAll();
    }

    @PostMapping("/post-user")
    public User createUser(@RequestBody User data) {
        return repo.save(data);
    }

    @GetMapping("/get-user/{username}")
    public User getUser(@PathVariable String username){

        return repo.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

}
