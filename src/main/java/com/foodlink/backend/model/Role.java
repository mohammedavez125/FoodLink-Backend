package com.foodlink.backend.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "role")
@Data
public class Role {
    @Id
    private ObjectId id;
    private String name;
    private final Set<Permission> permissions;
}
