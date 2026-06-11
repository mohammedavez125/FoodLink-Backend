package com.foodlink.backend.app.donation.model;


import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DropLocation {

    @JsonAlias("address")
    private String addressLine;

    private String city;

    private String state;

    private String pinCode;

    @NotNull
    private Double latitude;

    @NotNull
    private Double longitude;
}
