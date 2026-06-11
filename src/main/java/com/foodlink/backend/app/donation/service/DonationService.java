package com.foodlink.backend.app.donation.service;

import com.foodlink.backend.app.donation.dto.req.CreateDonationRequest;
import com.foodlink.backend.app.donation.dto.req.AcceptDonationRequest;
import com.foodlink.backend.app.donation.dto.req.UpdateDonationRequest;
import com.foodlink.backend.app.donation.dto.res.DonationResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DonationService {

    DonationResponse createDonation(
            String userId,
            CreateDonationRequest request
    );

    DonationResponse updateDonation(
            String donationId,
            String userId,
            UpdateDonationRequest request
    );

    DonationResponse acceptDonation(
            String donationId,
            String userId,
            AcceptDonationRequest request
    );

    DonationResponse dispatchDonation(
            String donationId,
            String userId
    );

    DonationResponse receiveDonation(
            String donationId,
            String userId
    );

    DonationResponse completeDonation(
            String donationId,
            String userId
    );

    void deleteDonation(
            String donationId,
            String userId
    );

    List<DonationResponse> getAvailableDonations();

    List<DonationResponse> getMyDonations(
            String userId
    );

    List<DonationResponse> getAcceptedDonations(
            String userId
    );

    List<DonationResponse> getDonorHistory(
            String userId
    );

    List<DonationResponse> getNgoHistory(
            String userId
    );
}
