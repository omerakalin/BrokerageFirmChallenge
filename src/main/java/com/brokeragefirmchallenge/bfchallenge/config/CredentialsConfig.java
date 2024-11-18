package com.brokeragefirmchallenge.bfchallenge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CredentialsConfig {

    @Value("${app.credentials.username}")
    private String username;

    @Value("${app.credentials.password}")
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
