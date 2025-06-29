package com.voting.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Tag(name = "Home", description = "Home controller for basic API information")
public class HomeController {

    @GetMapping("/")
    @Operation(summary = "Get API information", description = "Returns basic information about the voting system API")
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to Decentralized Voting System API");
        response.put("version", "1.0.0");
        response.put("status", "running");
        response.put("documentation", "/swagger-ui.html");
        response.put("endpoints", Map.of(
            "auth", "/auth/**",
            "elections", "/elections/**",
            "candidates", "/candidates/**",
            "votes", "/votes/**",
            "health", "/actuator/health"
        ));
        return response;
    }
} 