package com.foodlink.backend.security;

import com.foodlink.backend.model.Permission;
import com.foodlink.backend.model.role.Role;
import com.foodlink.backend.model.role.RoleName;
import com.foodlink.backend.model.User;
import com.foodlink.backend.repository.RoleRepository;
import com.foodlink.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class AdminUserInitializer {

    @Bean
    public CommandLineRunner createAdminUser(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {

        return args -> {

            Role adminRole = roleRepository.findByRoleName(RoleName.ADMIN)
                    .orElseThrow(() -> new RuntimeException("ADMIN role not found"));

            Role donorRole = roleRepository.findByRoleName(RoleName.DONOR)
                    .orElseThrow(() -> new RuntimeException("DONOR role not found"));

            Role ngoRole = roleRepository.findByRoleName(RoleName.NGO)
                    .orElseThrow(() -> new RuntimeException("NGO role not found"));

            grantPermission(
                    roleRepository,
                    adminRole,
                    Permission.DONATION_HISTORY_VIEW
            );
            grantPermission(
                    roleRepository,
                    donorRole,
                    Permission.DONATION_HISTORY_VIEW
            );
            grantPermission(
                    roleRepository,
                    ngoRole,
                    Permission.DONATION_HISTORY_VIEW
            );

            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin1234"));
                admin.setRole(adminRole);

                userRepository.save(admin);

                System.out.println("Admin created!");
            }

            if (userRepository.findByUsername("donor").isEmpty()) {
                User donor = new User();
                donor.setUsername("donor");
                donor.setPassword(passwordEncoder.encode("donor1234"));
                donor.setRole(donorRole);

                userRepository.save(donor);

                System.out.println("Donor created!");
            }
        };
    }

    private void grantPermission(
            RoleRepository roleRepository,
            Role role,
            Permission permission
    ) {

        if (role.getPermissions() == null) {
            role.setPermissions(new HashSet<>());
        }

        if (role.getPermissions().add(permission)) {
            roleRepository.save(role);
        }
    }
}
