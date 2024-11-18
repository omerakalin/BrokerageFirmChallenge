package com.brokeragefirmchallenge.bfchallenge.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoggingFilter());
        registrationBean.addUrlPatterns("/api/*");  // Only apply to the routes you want to log

        // Set the filter order; 1 is high priority, smaller numbers have higher priority
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
