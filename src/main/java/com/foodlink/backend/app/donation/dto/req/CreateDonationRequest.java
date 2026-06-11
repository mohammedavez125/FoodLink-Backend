package com.foodlink.backend.app.donation.dto.req;

import com.foodlink.backend.app.donation.model.FoodCategory;
import com.foodlink.backend.app.donation.model.PickupLocation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.Instant;

@Data
public class CreateDonationRequest {

    @NotBlank
    private String foodName;

    @NotNull
    private FoodCategory category;

    @NotNull
    @Positive
    private Integer quantity;

    private String description;

    @Valid
    @NotNull
    private PickupLocation pickupLocation;

    @Future
    private Instant expiryTime;
}