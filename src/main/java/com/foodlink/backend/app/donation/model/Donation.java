package com.foodlink.backend.app.donation.model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "donations")
public class Donation {

    @Id
    private String id;

    private String donorProfileId;

    private String acceptedNgoProfileId;

    private String foodName;

    private FoodCategory category;

    private Integer quantity;

    private String description;

    private PickupLocation pickupLocation;

    private DropLocation dropLocation;

    private DonationStatus status;

    private Integer estimatedMinutes;

    private Instant expiryTime;

    private Instant acceptedAt;

    private Instant dispatchedAt;

    private Instant receivedAt;

    private Instant completedAt;

    private Instant createdAt;

    private Instant updatedAt;
}
