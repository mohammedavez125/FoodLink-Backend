package com.foodlink.backend.app.ngo.dto.req;


import com.foodlink.backend.model.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateNgoProfileRequest {

    @NotBlank
    private String ngoName;

    @NotBlank
    private String registrationNumber;

    @NotBlank
    private String contactPerson;

    @NotBlank
    private String phone;

    @Valid
    @NotNull
    private Address address;

    private String description;
}