package com.foodlink.backend.app.donation.dto.res;

import com.foodlink.backend.app.donation.model.*;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class DonationResponse {

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
}
