package com.expense.tracker.repository;

import com.expense.tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom finder method
    Optional<User> findByUsername(String username);
}
