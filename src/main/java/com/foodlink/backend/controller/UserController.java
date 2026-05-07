package com.foodlink.backend.controller;

import com.foodlink.backend.repository.UserRepository;
import com.foodlink.backend.model.User;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
