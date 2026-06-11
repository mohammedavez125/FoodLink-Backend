package com.foodlink.backend.app.donor;


import com.foodlink.backend.app.donor.dto.req.CreateDonorProfileRequest;
import com.foodlink.backend.app.donor.dto.req.UpdateDonorProfileRequest;
import com.foodlink.backend.app.donor.dto.res.DonorProfileResponse;
import com.foodlink.backend.app.donor.service.DonorService;
import com.foodlink.backend.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("donor/profile")
public class DonorController {

    private final DonorService donorService;

    @GetMapping("/get-my-profile")
    @PreAuthorize("hasAuthority('DONOR_PROFILE_READ')")
    public DonorProfileResponse getMyProfile(
            @AuthenticationPrincipal User user
    ) {

        return donorService.getMyProfile(
                String.valueOf(user.getId())
        );
    }

    @PutMapping("/update-my-profile")
    @PreAuthorize("hasAuthority('DONOR_PROFILE_UPDATE')")
    public DonorProfileResponse updateProfile(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UpdateDonorProfileRequest request
    ) {

        return donorService.updateProfile(
                String.valueOf(user.getId()),
                request
        );
    }

    @DeleteMapping("/delete-my-profile")
    @PreAuthorize("hasAuthority('DONOR_PROFILE_DELETE')")
    public void deleteProfile(
            @AuthenticationPrincipal User user
    ) {

        donorService.deleteProfile(
                String.valueOf(user.getId())
        );
    }
}