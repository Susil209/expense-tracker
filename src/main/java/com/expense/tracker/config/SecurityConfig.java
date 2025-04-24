package com.expense.tracker.config;

import com.expense.tracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    protected  SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/h2-console/**").permitAll()
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
//
    }

    @Bean
    protected UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User
                .withUsername("admin")
                .password(passwordEncoder.encode("password"))
                .roles("ADMIN").build());

        return manager;
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
