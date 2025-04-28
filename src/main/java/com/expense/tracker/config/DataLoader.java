package com.expense.tracker.config;

import com.expense.tracker.model.Category;
import com.expense.tracker.model.User;
import com.expense.tracker.repository.CategoryRepository;
import com.expense.tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
@Profile("dev")
@RequiredArgsConstructor
public class DataLoader {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner loadAdminUser() {
        return args -> {
            // Check if admin user already exists
            if (userRepository.findByUsername("admin").isEmpty()) {
                // Create a new admin user
                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("password")) // encode the password
                        .role("ADMIN")
                        .build();
                userRepository.save(admin);
                System.out.println("Default Admin user created! ✅");
            } else {
                System.out.println("Admin user already exists. Skipping creation. ✅");
            }
        };
    }

    @Bean
    public CommandLineRunner loadCategories(CategoryRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                repo.save(new Category(null, "Groceries", List.of()));
                repo.save(new Category(null, "Utilities", List.of()));
                repo.save(new Category(null, "Travel", List.of()));
                System.out.println("Seeded default categories");
            }
        };
    }

}
