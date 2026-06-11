package com.foodlink.backend.app.donor.model;

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
@Document(collection = "donors")
public class DonorProfile {

    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

    private DonorType donorType;

    private String organizationName;

    private String contactPerson;

    private String phone;

    private Address address;

    private String description;

    private boolean active;

    private Instant createdAt;

    private Instant updatedAt;
}