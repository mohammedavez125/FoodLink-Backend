package com.foodlink.backend.app.ngo;

import com.foodlink.backend.app.ngo.dto.req.CreateNgoProfileRequest;
import com.foodlink.backend.app.ngo.dto.req.UpdateNgoProfileRequest;
import com.foodlink.backend.app.ngo.dto.res.NgoProfileResponse;
import com.foodlink.backend.app.ngo.service.NgoService;
import com.foodlink.backend.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("ngo/profile")
public class NgoController {

    private final NgoService ngoService;


    @GetMapping("/get-my-profile")
    @PreAuthorize("hasAuthority('NGO_PROFILE_READ')")
    public NgoProfileResponse getMyProfile(
            @AuthenticationPrincipal User user
    ) {

        return ngoService.getMyProfile(
                String.valueOf(user.getId())
        );
    }

    @PutMapping("/update-my-profile")
    @PreAuthorize("hasAuthority('NGO_PROFILE_UPDATE')")
    public NgoProfileResponse updateProfile(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UpdateNgoProfileRequest request
    ) {

        return ngoService.updateProfile(
                String.valueOf(user.getId()),
                request
        );
    }

    @DeleteMapping("/delete-my-profile")
    @PreAuthorize("hasAuthority('NGO_PROFILE_DELETE')")
    public void deleteProfile(
            @AuthenticationPrincipal User user
    ) {

        ngoService.deleteProfile(
                String.valueOf(user.getId())
        );
    }
}