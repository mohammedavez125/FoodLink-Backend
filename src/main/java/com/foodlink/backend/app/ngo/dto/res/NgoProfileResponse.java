package com.foodlink.backend.app.ngo.dto.res;


import com.foodlink.backend.model.Address;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NgoProfileResponse {

    private String id;

    private String userId;

    private String ngoName;

    private String registrationNumber;

    private String contactPerson;

    private String phone;

    private Address address;

    private String description;

    private boolean active;
}
