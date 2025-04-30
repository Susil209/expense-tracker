package com.expense.tracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError err : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(err.getField(), err.getDefaultMessage());
        }

        Map<String, Object> response = new HashMap<>();
        response.put("error", "Validation failed");
        response.put("fields", fieldErrors);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }
}

/*
  What a Validation Error Looks Like

  If you POST:
  {
    "amount": -5,
    "description": "",
    "date": "2025-12-31",
    "categoryId": 0
  }

  You’ll get:
  HTTP/1.1 400 Bad Request
Content-Type: application/json

{
  "error": "Validation failed",
  "fields": {
    "amount": "Amount must be at least 0.01",
    "description": "Description is required",
    "date": "Date cannot be in the future",
    "categoryId": "Category ID must be a positive number"
  }
}

 */