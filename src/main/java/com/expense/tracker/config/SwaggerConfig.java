package com.expense.tracker.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Expense Tracker API")
                        .version("1.0")
                        .description("This is a backend API for managing your personal expenses.")
                        .contact(new Contact()
                                .name("Susil Kumar Nayak")
                                .email("susilnayak2015@gmail.com")
                                .url("https://github.com/Susil209"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}
