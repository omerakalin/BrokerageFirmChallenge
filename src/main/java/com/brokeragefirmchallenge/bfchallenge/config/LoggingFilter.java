package com.brokeragefirmchallenge.bfchallenge.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Avoid logging for login/authentication or error routes
        if (((HttpServletRequest) request).getRequestURI().startsWith("/auth")) {
            chain.doFilter(request, response);  // Skip filter for login
            return;
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Log request information
        logger.info("Request URL: {} | Method: {}", httpRequest.getRequestURL(), httpRequest.getMethod());

        // Proceed with the next filter in the chain
        chain.doFilter(request, response);

        // Log response information
        logger.info("Response Status: {}", httpResponse.getStatus());
    }
}
