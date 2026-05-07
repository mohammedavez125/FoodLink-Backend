package com.foodlink.backend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private Integer id;
    private String firstname;
    private String lastname;
    @Indexed(unique = true)
    private String email;
    private String password;
    private int phone;
    private String address;
    private Role role;
}