package com.foodlink.backend.repository;

import com.foodlink.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {
}
