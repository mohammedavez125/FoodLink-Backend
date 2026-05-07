package com.foodlink.backend.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import java.io.IOException;

public class OpenAPIController {

    @Hidden
    @RequestMapping(value = "/")
    public void redirect(HttpServletResponse res) throws IOException {
        res.sendRedirect("/swagger-ui/index.html");
    }
}
