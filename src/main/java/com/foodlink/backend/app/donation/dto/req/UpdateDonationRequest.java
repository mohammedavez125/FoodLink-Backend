package com.foodlink.backend.app.donation.dto.req;

import com.foodlink.backend.app.donation.model.FoodCategory;
import com.foodlink.backend.app.donation.model.PickupLocation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.Instant;

@Data
public class UpdateDonationRequest {

    @NotBlank
    private String foodName;

    @NotNull
    private FoodCategory category;

    @Positive
    private Integer quantity;

    private String description;

    @Valid
    @NotNull
    private PickupLocation pickupLocation;

    private Instant expiryTime;
}
