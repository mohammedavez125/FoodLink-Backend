package com.foodlink.backend.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class OpenAPIController {

    @Hidden
    @RequestMapping(value = "/")
    public void redirect(HttpServletResponse res) throws IOException {
        res.sendRedirect("/swagger-ui/index.html");
    }

//  TEST
@GetMapping("/debug")
    public String debug(Authentication authentication) {

        return authentication.getAuthorities().toString();
    }
}
