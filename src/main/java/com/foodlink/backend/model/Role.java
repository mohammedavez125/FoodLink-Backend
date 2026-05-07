package com.foodlink.backend.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "role")
public @Data class Role {
    private String name;
}