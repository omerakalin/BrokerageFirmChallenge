package com.brokeragefirmchallenge.bfchallenge.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/healthcheck")
    public String healthCheck() {
        try {
            // Attempting a simple query to check H2 database connection
            jdbcTemplate.execute("SELECT 1");
            return "Healthy: H2 Database is connected";
        } catch (Exception e) {
            return "Unhealthy: H2 Database connectivity issue";
        }
    }
}
