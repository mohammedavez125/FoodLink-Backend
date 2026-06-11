package com.foodlink.backend.app.ngo.dto.req;


import com.foodlink.backend.model.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateNgoProfileRequest {

    @NotBlank
    private String ngoName;

    @NotBlank
    private String registrationNumber;

    @NotBlank
    private String contactPerson;

    @NotBlank
    private String phone;

    @Valid
    private Address address;

    private String description;

    private boolean active;
}