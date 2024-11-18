package com.brokeragefirmchallenge.bfchallenge.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class OpenApiConfig {

  /** to avoid conflict in this case with SwaggerConfig (customOpenAPI)
     @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Brokerage Firm API")
                        .version("1.0.0")
                        .description("API documentation for the Brokerage Firm Challenge project"));
    }
    */
}
