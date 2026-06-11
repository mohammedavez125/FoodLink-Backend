package com.foodlink.backend.app.donor.dto.res;

import com.foodlink.backend.app.donor.model.DonorType;
import com.foodlink.backend.model.Address;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DonorProfileResponse {

    private String id;

    private String userId;

    private DonorType donorType;

    private String organizationName;

    private String contactPerson;

    private String phone;

    private Address address;

    private String description;

    private boolean active;
}
