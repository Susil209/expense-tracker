package com.expense.tracker.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseDto {
    private Long id;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be at least 0.01")
    private BigDecimal amount;

    @NotBlank(message = "Description is required")
    @Size(max = 200, message = "Description must be at most 200 characters")
    private String description;

    @NotNull(message = "Date is required")
    @PastOrPresent(message = "Date cannot be in the future")
    private LocalDate date;

    @NotNull(message = "Category ID is required")
    @Positive(message = "Category ID must be a positive number")
    private Long categoryId;
}
