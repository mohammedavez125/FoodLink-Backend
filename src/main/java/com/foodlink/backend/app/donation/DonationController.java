package com.foodlink.backend.app.donation;

import com.foodlink.backend.app.donation.dto.req.CreateDonationRequest;
import com.foodlink.backend.app.donation.dto.req.AcceptDonationRequest;
import com.foodlink.backend.app.donation.dto.req.UpdateDonationRequest;
import com.foodlink.backend.app.donation.dto.res.DonationResponse;
import com.foodlink.backend.app.donation.service.DonationService;
import com.foodlink.backend.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("donations")
@Tag(name = "Donations", description = "Donation detail and workflow APIs")
public class DonationController {

    private final DonationService donationService;

    @PostMapping("/create-donation")
    @PreAuthorize("hasAuthority('DONATION_CREATE')")
    @Operation(summary = "Create donation", description = "DONOR creates a new AVAILABLE donation.")
    public DonationResponse createDonation(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CreateDonationRequest request
    ) {

        return donationService.createDonation(
                String.valueOf(user.getId()),
                request
        );
    }

    @PutMapping("/{donationId}")
    @PreAuthorize("hasAuthority('DONATION_UPDATE')")
    @Operation(
            summary = "Update donation details",
            description = "DONOR updates editable donation details. Allowed only while status is AVAILABLE."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Donation details updated"),
            @ApiResponse(responseCode = "400", description = "Donation is not AVAILABLE or request is invalid"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Missing DONATION_UPDATE permission or caller is not owner")
    })
    public DonationResponse updateDonation(
            @PathVariable String donationId,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UpdateDonationRequest request
    ) {
        return donationService.updateDonation(
                donationId,
                String.valueOf(user.getId()),
                request
        );
    }

    @DeleteMapping("/delete-donation/{donationId}")
    @PreAuthorize("hasAuthority('DONATION_DELETE')")
    @Operation(summary = "Delete donation", description = "DONOR deletes own donation. Allowed only while status is AVAILABLE.")
    public void deleteDonation(
            @PathVariable String donationId,
            @AuthenticationPrincipal User user
    ) {

        donationService.deleteDonation(
                donationId,
                String.valueOf(user.getId())
        );
    }

    @GetMapping("/available-donations")
    @PreAuthorize("hasAuthority('DONATION_VIEW')")
    @Operation(summary = "List available donations")
    public List<DonationResponse> getAvailableDonations() {

        return donationService.getAvailableDonations();
    }

    @GetMapping("/my-accepted-donations")
    @PreAuthorize("hasAuthority('DONATION_VIEW_ACCEPTED')")
    @Operation(summary = "List accepted donations", description = "NGO lists donations accepted by its profile.")
    public List<DonationResponse> getAcceptedDonations(
            @AuthenticationPrincipal User user
    ) {

        return donationService.getAcceptedDonations(
                String.valueOf(user.getId())
        );
    }

    @GetMapping("/my-donations")
    @PreAuthorize("hasAuthority('DONATION_VIEW_OWN')")
    @Operation(summary = "List my donations", description = "DONOR lists donations created by its profile.")
    public List<DonationResponse> getMyDonations(
            @AuthenticationPrincipal User user
    ) {

        return donationService.getMyDonations(
                String.valueOf(user.getId())
        );
    }

    @GetMapping("/history/donor")
    @PreAuthorize("hasAuthority('DONATION_HISTORY_VIEW')")
    @Operation(
            summary = "Get donor donation history",
            description = "Donation history contains only COMPLETED donations belonging to the current donor profile."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Completed donor donations returned"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Missing DONATION_HISTORY_VIEW permission")
    })
    public List<DonationResponse> getDonorHistory(
            @AuthenticationPrincipal User user
    ) {

        return donationService.getDonorHistory(
                String.valueOf(user.getId())
        );
    }

    @GetMapping("/history/ngo")
    @PreAuthorize("hasAuthority('DONATION_HISTORY_VIEW')")
    @Operation(
            summary = "Get NGO donation history",
            description = "Donation history contains only COMPLETED donations accepted by the current NGO profile."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Completed NGO donations returned"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Missing DONATION_HISTORY_VIEW permission")
    })
    public List<DonationResponse> getNgoHistory(
            @AuthenticationPrincipal User user
    ) {

        return donationService.getNgoHistory(
                String.valueOf(user.getId())
        );
    }

    @PostMapping("/{donationId}/accept")
    @PreAuthorize("hasAuthority('DONATION_ACCEPT')")
    @Operation(
            summary = "Accept donation",
            description = "NGO accepts an AVAILABLE donation, provides drop location, and moves it to ACCEPTED."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Donation accepted"),
            @ApiResponse(responseCode = "400", description = "Donation is not AVAILABLE, expired, or drop location is invalid"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Missing DONATION_ACCEPT permission")
    })
    public DonationResponse acceptDonation(
            @PathVariable String donationId,
            @AuthenticationPrincipal User user,
            @Valid @RequestBody AcceptDonationRequest request
    ) {

        return donationService.acceptDonation(
                donationId,
                String.valueOf(user.getId()),
                request
        );
    }

    @PatchMapping("/{donationId}/dispatch")
    @PreAuthorize("hasAuthority('DELIVERY_START_TRANSIT')")
    @Operation(summary = "Dispatch donation", description = "DONOR dispatches own ACCEPTED donation and moves it to DISPATCHED.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Donation dispatched"),
            @ApiResponse(responseCode = "400", description = "Donation is not ACCEPTED"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Missing DELIVERY_START_TRANSIT permission or caller is not owner")
    })
    public DonationResponse dispatchDonation(
            @PathVariable String donationId,
            @AuthenticationPrincipal User user
    ) {

        return donationService.dispatchDonation(
                donationId,
                String.valueOf(user.getId())
        );
    }

    @PatchMapping("/{donationId}/receive")
    @PreAuthorize("hasAuthority('DELIVERY_PICKUP')")
    @Operation(summary = "Receive donation", description = "Accepted NGO marks a DISPATCHED donation as RECEIVED.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Donation received"),
            @ApiResponse(responseCode = "400", description = "Donation is not DISPATCHED"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Missing DELIVERY_PICKUP permission or caller is not accepted NGO")
    })
    public DonationResponse receiveDonation(
            @PathVariable String donationId,
            @AuthenticationPrincipal User user
    ) {

        return donationService.receiveDonation(
                donationId,
                String.valueOf(user.getId())
        );
    }

    @PatchMapping("/{donationId}/complete")
    @PreAuthorize("hasAuthority('DELIVERY_COMPLETE')")
    @Operation(summary = "Complete donation", description = "Accepted NGO completes a RECEIVED donation.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Donation completed"),
            @ApiResponse(responseCode = "400", description = "Donation is not RECEIVED"),
            @ApiResponse(responseCode = "401", description = "Authentication required"),
            @ApiResponse(responseCode = "403", description = "Missing DELIVERY_COMPLETE permission or caller is not accepted NGO")
    })
    public DonationResponse completeDonation(
            @PathVariable String donationId,
            @AuthenticationPrincipal User user
    ) {

        return donationService.completeDonation(
                donationId,
                String.valueOf(user.getId())
        );
    }
}
