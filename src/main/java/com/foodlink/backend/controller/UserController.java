package com.foodlink.backend.controller;

import com.foodlink.backend.model.User;
import com.foodlink.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository repo;

    //  GET ALL USERS
    @GetMapping("/get-users")
    @PreAuthorize("hasAuthority('USER_READ')")
    public List<User> getAllUsers() {
        return repo.findAll();
    }



    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping("/get-user/{username}")
    public User getUser(@PathVariable String username) {
        System.out.println("Inside ");
        return repo.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));
    }

    @DeleteMapping("/delete-user/{userId}")
    @PreAuthorize("hasAuthority('USER_DELETE')")
    public void deleteUser(@PathVariable String userId) {
        repo.deleteById(userId);
    }

    @PutMapping("/update-user/{userId}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public User updateUser(@PathVariable String userId, @RequestBody User user) {
        user.setId(new org.bson.types.ObjectId(userId));
        return repo.save(user);
    }
}
