package com.expense.tracker.repository;

import com.expense.tracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Custom finder to get category by name
    Optional<Category> findByName(String name);

    // Check if category with name already exists (for validation or uniqueness)
    boolean existsByName(String name);

    @Query("SELECT c FROM Category c WHERE c.id = :categoryId")
    Optional<Category> findCategoryById(Long categoryId);
}
