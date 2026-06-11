package com.foodlink.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.foodlink.backend.model.role.Role;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Document(collection = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    private ObjectId id;
    @DBRef
    private Role role;

    @Indexed(unique = true)
    private String username;
    private String firstname;
    private String lastname;

    @Indexed(unique = true)
    private String email;
    @JsonIgnore
    private String password;
    private String phone;
    private Address address;

    private AuthProvider provider;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        if (role == null) {
            return authorities;
        }

        if (role.getRoleName() != null) {
            authorities.add(
                    new SimpleGrantedAuthority(
                            "ROLE_" + role.getRoleName()
                    )
            );
        }

        if (role.getPermissions() != null) {
            role.getPermissions()
                    .forEach(
                            permission -> authorities.add(
                                    new SimpleGrantedAuthority(
                                            permission.name()
                                    )
                            )
                    );
        }

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
