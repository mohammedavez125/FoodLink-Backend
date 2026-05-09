package com.foodlink.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.stream.Collectors;

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
    private String address;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        Set<SimpleGrantedAuthority> permissionAuthorities = role.getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.name()))
                .collect(Collectors.toSet());

        // Role, Permission authority
        authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
        authorities.addAll(permissionAuthorities);

        System.out.println("getAuthorities called for: " + username);
        System.out.println(authorities);

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