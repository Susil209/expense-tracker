package com.expense.tracker.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic API response wrapper
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWrapper<T> {
    private String message;
    private T data;
}
