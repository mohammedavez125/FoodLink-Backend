package com.foodlink.backend.security.service.impl;

import com.foodlink.backend.app.donor.model.DonorProfile;
import com.foodlink.backend.app.donor.model.DonorType;
import com.foodlink.backend.app.donor.repository.DonorProfileRepository;
import com.foodlink.backend.app.ngo.model.NgoProfile;
import com.foodlink.backend.app.ngo.repository.NgoProfileRepository;
import com.foodlink.backend.model.AuthProvider;
import com.foodlink.backend.model.role.Role;
import com.foodlink.backend.model.role.RoleName;
import com.foodlink.backend.model.User;
import com.foodlink.backend.repository.RoleRepository;
import com.foodlink.backend.repository.UserRepository;
import com.foodlink.backend.security.dto.request.AuthRequestDto;
import com.foodlink.backend.security.dto.request.RegisterDonorRequestDto;
import com.foodlink.backend.security.dto.request.RegisterNgoRequestDto;
import com.foodlink.backend.security.dto.response.LoginResponseDto;
import com.foodlink.backend.security.dto.response.RegisterUserResponseDto;
import com.foodlink.backend.security.jwt.JWTUtil;
import com.foodlink.backend.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final DonorProfileRepository donorProfileRepository;
    private final NgoProfileRepository  ngoProfileRepository;

    @Override
    public RegisterUserResponseDto registerDonor(
            RegisterDonorRequestDto request
    ) {

        validateUser(
                request.getUsername(),
                request.getEmail()
        );

        Role role = roleRepository
                .findByRoleName(RoleName.DONOR)
                .orElseThrow(
                        () -> new RuntimeException(
                                "DONOR role not found"
                        )
                );

        User user = new User();

        user.setUsername(request.getUsername());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole(role);
        user.setProvider(AuthProvider.LOCAL);

        User savedUser =
                userRepository.save(user);

        var donorReq =
                request.getDonorProfile();

        DonorProfile donorProfile =
                DonorProfile.builder()
                        .userId(String.valueOf(savedUser.getId()))
                        .donorType(
                                donorReq.getDonorType()
                        )
                        .organizationName(
                                donorReq.getOrganizationName()
                        )
                        .contactPerson(
                                donorReq.getContactPerson()
                        )
                        .phone(
                                donorReq.getPhone()
                        )
                        .address(
                                donorReq.getAddress()
                        )
                        .description(
                                donorReq.getDescription()
                        )
                        .active(true)
                        .build();

        donorProfileRepository.save(
                donorProfile
        );

        return new RegisterUserResponseDto(
                savedUser.getUsername(),
                savedUser.getRole()
        );
    }

    @Override
    public RegisterUserResponseDto registerNgo(
            RegisterNgoRequestDto request
    ) {

        validateUser(
                request.getUsername(),
                request.getEmail()
        );

        Role role = roleRepository
                .findByRoleName(RoleName.NGO)
                .orElseThrow(
                        () -> new RuntimeException(
                                "NGO role not found"
                        )
                );

        User user = new User();

        user.setUsername(request.getUsername());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );

        user.setRole(role);
        user.setProvider(AuthProvider.LOCAL);

        User savedUser =
                userRepository.save(user);

        var ngoReq =
                request.getNgoProfile();

        NgoProfile ngoProfile =
                NgoProfile.builder()
                        .userId(String.valueOf(savedUser.getId()))
                        .ngoName(
                                ngoReq.getNgoName()
                        )
                        .registrationNumber(
                                ngoReq.getRegistrationNumber()
                        )
                        .contactPerson(
                                ngoReq.getContactPerson()
                        )
                        .phone(
                                ngoReq.getPhone()
                        )
                        .address(
                                ngoReq.getAddress()
                        )
                        .description(
                                ngoReq.getDescription()
                        )
                        .active(true)
                        .build();

        ngoProfileRepository.save(
                ngoProfile
        );

        return new RegisterUserResponseDto(
                savedUser.getUsername(),
                savedUser.getRole()
        );
    }

    @Override
    public LoginResponseDto handleOauth2LoginRequest(OAuth2User oAuth2User) {

        String email = oAuth2User.getAttribute("email");
        String firstname = oAuth2User.getAttribute("given_name");
        String lastname = oAuth2User.getAttribute("family_name");

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {

                    Role donorRole = roleRepository
                            .findByRoleName(RoleName.DONOR)
                            .orElseThrow(() ->
                                    new RuntimeException("DONOR role not found"));

                    User newUser = new User();

                    newUser.setEmail(email);
                    newUser.setFirstname(firstname);
                    newUser.setLastname(lastname);
                    newUser.setUsername(email);
                    newUser.setProvider(AuthProvider.GOOGLE);
                    newUser.setRole(donorRole);

                    User savedUser = userRepository.save(newUser);

                    // Create default donor profile
                    DonorProfile donorProfile = DonorProfile.builder()
                            .userId(String.valueOf(savedUser.getId()))
                            .donorType(DonorType.INDIVIDUAL)
                            .organizationName(null)
                            .contactPerson(
                                    firstname +
                                            (lastname != null ? " " + lastname : "")
                            )
                            .phone(null)
                            .address(null)
                            .description("Registered via Google OAuth")
                            .active(true)
                            .build();

                    donorProfileRepository.save(donorProfile);

                    return savedUser;
                });

        String token = jwtUtil.generateToken(user.getUsername());

        return new LoginResponseDto(
                token,
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

    @Override
    public LoginResponseDto login(AuthRequestDto authReq) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authReq.getUsername(),
                        authReq.getPassword()
                )
        );

        User user = userRepository.findByUsername(authReq.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getUsername());

        return new LoginResponseDto(
                token,
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

    private void validateUser(
            String username,
            String email
    ) {

        if (userRepository
                .findByUsername(username)
                .isPresent()) {

            throw new RuntimeException(
                    "Username already exists"
            );
        }

        if (userRepository
                .findByEmail(email)
                .isPresent()) {

            throw new RuntimeException(
                    "Email already exists"
            );
        }
    }
}