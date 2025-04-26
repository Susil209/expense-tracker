package com.expense.tracker.repository;

import com.expense.tracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    // Custom query to get expenses by category ID
    @Query("SELECT e FROM Expense e WHERE e.category.id = ?1")
    List<Expense> findByCategoryId(Long categoryId);

    // Custom query to get expenses within a date range
    @Query("SELECT e FROM Expense e WHERE e.date BETWEEN ?1 and ?2")
    List<Expense> findByDateRange(LocalDate startDate, LocalDate endDate);

    // Custom query to get total expenses by category ID
    @Query("SELECT Sum(e.amount) FROM Expense e WHERE e.category.id = ?1")
    BigDecimal getTotalAmountByCategoryId(Long categoryId);
}
