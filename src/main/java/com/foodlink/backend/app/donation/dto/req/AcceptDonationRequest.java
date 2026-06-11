package com.foodlink.backend.app.donation.dto.req;

import com.foodlink.backend.app.donation.model.DropLocation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AcceptDonationRequest {

    @Valid
    @NotNull
    private DropLocation dropLocation;
}