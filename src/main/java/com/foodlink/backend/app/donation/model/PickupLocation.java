package com.foodlink.backend.app.donation.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PickupLocation {

    private String addressLine;

    private String city;

    private String state;

    private String pinCode;

    private Double latitude;

    private Double longitude;
}