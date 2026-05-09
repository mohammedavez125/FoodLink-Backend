package com.foodlink.backend.service;

import com.foodlink.backend.model.Role;
import com.foodlink.backend.model.User;
import com.foodlink.backend.repository.RoleRepository;
import com.foodlink.backend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminUserInitializer {

    @Bean
    public CommandLineRunner createAdminUser(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {

        return args -> {

            Role adminRole = roleRepository.findByName("ADMIN")
                    .orElseThrow(() -> new RuntimeException("ADMIN role not found"));
            Role userRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("USER role not found"));

            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin1234"));
                admin.setRole(adminRole);
                userRepository.save(admin);
                System.out.println("Admin created!");
            }

            if (userRepository.findByUsername("user").isEmpty()) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user1234"));
                user.setRole(userRole);
                userRepository.save(user);
                System.out.println("User created!");
            }
        };
    }
}