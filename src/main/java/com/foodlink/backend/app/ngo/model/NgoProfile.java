package com.foodlink.backend.app.ngo.model;


import com.foodlink.backend.model.Address;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ngos")
public class NgoProfile {

    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

    private String ngoName;

    private String registrationNumber;

    private String contactPerson;

    private String phone;

    private Address address;

    private String description;

    private boolean active;

    private Instant createdAt;

    private Instant updatedAt;
}