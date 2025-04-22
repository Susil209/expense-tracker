package com.expense.tracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/test", "/h2-console/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(Customizer.withDefaults()) // uses the default login page
                .csrf(csrf -> csrf.disable())         // disable CSRF
                .headers(headers -> headers.frameOptions(frame -> frame.disable())) // allow H2 console to render
                .build();

        // or

//        return http
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/test", "/h2-console/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(Customizer.withDefaults()) // uses the default login page
//                .csrf(AbstractHttpConfigurer::disable)         // disable CSRF
//                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // allow H2 console to render
//                .build();

    }
}
