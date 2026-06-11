package com.foodlink.backend.security.oauth2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodlink.backend.security.dto.response.LoginResponseDto;
import com.foodlink.backend.security.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final AuthService authService;
    private final ObjectMapper objectMapper;

//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, @NonNull Authentication authentication) throws IOException {
//        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
//        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
//        LoginResponseDto loginResponse = authService.handleOauth2LoginRequest(oAuth2User);
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        response.getWriter().write(objectMapper.writeValueAsString(loginResponse));
//        response.getWriter().flush();
//    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {

        OAuth2User oAuth2User =
                (OAuth2User) authentication.getPrincipal();

        LoginResponseDto loginResponse =
                authService.handleOauth2LoginRequest(oAuth2User);

        String redirectUrl =
                "http://localhost:5173/login/callback"
                        + "?token=" + URLEncoder.encode(loginResponse.getToken(), StandardCharsets.UTF_8)
                        + "&username=" + URLEncoder.encode(loginResponse.getUsername(), StandardCharsets.UTF_8)
                        + "&email=" + URLEncoder.encode(loginResponse.getEmail(), StandardCharsets.UTF_8)
                        + "&role=" + URLEncoder.encode(
                        objectMapper.writeValueAsString(loginResponse.getRole()),
                        StandardCharsets.UTF_8
                );

        response.sendRedirect(redirectUrl);
    }
}