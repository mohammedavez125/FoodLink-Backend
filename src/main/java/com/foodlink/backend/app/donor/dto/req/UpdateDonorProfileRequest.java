package com.foodlink.backend.app.donor.dto.req;

import com.foodlink.backend.model.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateDonorProfileRequest {

    @NotBlank
    private String organizationName;

    @NotBlank
    private String contactPerson;

    @NotBlank
    private String phone;

    @Valid
    private Address address;

    private String description;

    private boolean active;
}