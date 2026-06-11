package com.foodlink.backend.app.donor.dto.req;

import com.foodlink.backend.app.donor.model.DonorType;
import com.foodlink.backend.model.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateDonorProfileRequest {

    @NotNull
    private DonorType donorType;

    @NotBlank
    private String organizationName;

    @NotBlank
    private String contactPerson;

    @NotBlank
    private String phone;

    @Valid
    @NotNull
    private Address address;

    private String description;
}