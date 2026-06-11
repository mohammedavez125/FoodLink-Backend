package com.foodlink.backend.controller;

import com.foodlink.backend.repository.RoleRepository;
import com.foodlink.backend.model.role.Role;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleRepository repo;

    @GetMapping("/get-roles")
    @PreAuthorize("hasAuthority('ROLE_READ')")
    public List<Role> getAllPosts(){
        return repo.findAll();
    }

    @PostMapping("/post-role")
    @PreAuthorize("hasAuthority('ROLE_CREATE')")
    public Role createRole(@RequestBody Role data) {
        return repo.save(data);
    }

    @PutMapping("/update-role/{roleId}")
    @PreAuthorize("hasAuthority('ROLE_UPDATE')")
    public Role updateRole(@PathVariable String roleId, @RequestBody Role data) {
        data.setId(new org.bson.types.ObjectId(roleId));
        return repo.save(data);
    }

    @DeleteMapping("/delete-role/{roleId}")
    @PreAuthorize("hasAuthority('ROLE_DELETE')")
    public void deleteRole(@PathVariable String roleId) {
        repo.deleteById(roleId);
    }
}
