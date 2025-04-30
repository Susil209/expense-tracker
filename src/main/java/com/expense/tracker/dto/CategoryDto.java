package com.expense.tracker.dto;

import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private Long id;

    @NotBlank(message = "Category name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;
}

