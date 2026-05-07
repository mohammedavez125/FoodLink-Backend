package com.foodlink.backend.repository;

import com.foodlink.backend.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role,String> {
}
