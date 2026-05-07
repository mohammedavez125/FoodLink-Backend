package com.foodlink.backend.controller;

import com.foodlink.backend.repository.RoleRepository;
import com.foodlink.backend.model.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class RoleController {

    @Autowired
    RoleRepository repo;

    @GetMapping("/get-roles")
    public List<Role> getAllPosts(){
        return repo.findAll();
    }

    @PostMapping("/post-role")
    public Role createRole(@RequestBody Role data) {
        return repo.save(data);
    }
}
