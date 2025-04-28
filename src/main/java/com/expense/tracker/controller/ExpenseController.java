package com.expense.tracker.controller;

import com.expense.tracker.dto.ExpenseDto;
import com.expense.tracker.payload.ResponseWrapper;
import com.expense.tracker.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Tag(name = "Expense Controller", description = "REST endpoints for managing expenses")
@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@Slf4j
public class ExpenseController {

    private final ExpenseService expenseService;

    // create expense
    @Operation(summary = "Create a new expense.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Expense created successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid request data.")
    })
    @PostMapping
    public ResponseEntity<ResponseWrapper<ExpenseDto>> createExpense(
            @Parameter(description = "Expense data to be created") @Valid @RequestBody ExpenseDto expenseDto){
        log.info("Received request to create expense: {}", expenseDto);

        ExpenseDto createExpense = expenseService.saveExpense(expenseDto);
        log.info("Expense created successfully with id={}", createExpense.getId());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createExpense.getId())
                .toUri();

        ResponseWrapper<ExpenseDto> body = new ResponseWrapper<>(
                "Expense created successfully",
                createExpense
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .location(location)
                .body(body);
    }

    @Operation(summary = "Get all expenses")
    @ApiResponse(responseCode = "200", description = "List of all expenses")
    @GetMapping
    public ResponseEntity<ResponseWrapper<List<ExpenseDto>>> getAllExpenses(){
        log.info("Received request to list all expenses");
        List<ExpenseDto> expenses = expenseService.getAllExpenses();
        log.debug("Found {} expenses", expenses.size());

        ResponseWrapper<List<ExpenseDto>> body = new ResponseWrapper<>(
                "Expenses retrieved successfully,", expenses
        );

        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Get expenses by category ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expenses for category found"),
            @ApiResponse(responseCode = "404", description = "No expenses found for category")
    })
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ResponseWrapper<List<ExpenseDto>>> getExpensesByCategory(
            @Parameter(description = "Category ID to filter expense") @PathVariable Long categoryId){

        log.info("Received request to list expenses for categoryId={}", categoryId);
        List<ExpenseDto> expenses = expenseService.getExpensesByCategoryId(categoryId);
        log.debug("Found {} expenses for categoryId={}", expenses.size(), categoryId);

        ResponseWrapper<List<ExpenseDto>> body = new ResponseWrapper<>(
                "Expenses for category retrieved successfully", expenses
        );
        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Get expenses within a date range")
    @ApiResponse(responseCode = "200", description = "Expenses within the specified date range")
    @GetMapping("/date-range")
    public ResponseEntity<ResponseWrapper<List<ExpenseDto>>> getExpensesByDateRange(
            @Parameter(description = "Start date of the range") @RequestParam LocalDate startDate,
            @Parameter(description = "End date of the range") @RequestParam LocalDate endDate
            ){
        log.info("Received request for expenses from {} to {}", startDate, endDate);
        List<ExpenseDto> expenses = expenseService.getExpensesByDateRange(startDate, endDate);
        log.debug("Found {} expenses between {} and {}", expenses.size(), startDate, endDate);

        ResponseWrapper<List<ExpenseDto>> body = new ResponseWrapper<>(
                String.format("Expenses from %s to %s retrieved successfully", startDate, endDate),
                expenses
        );

        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Get total expenses for a category")
    @ApiResponse(responseCode = "200", description = "Total expenses for category found")
    @GetMapping("/total/category/{categoryId}")
    public ResponseEntity<ResponseWrapper<BigDecimal>> getTotalExpenseByCategory(
            @Parameter(description = "Category ID to get total expenses") @PathVariable Long categoryId
    ){
        log.info("Received request for total expenses of categoryId={}", categoryId);
        BigDecimal totalExpenses = expenseService.getTotalExpensesByCategoryId(categoryId);
        log.debug("Total expenses for categoryId={} is {}", categoryId, totalExpenses);

        ResponseWrapper<BigDecimal> body = new ResponseWrapper<>(
                "Total expenses for category retrieved successfully",
                totalExpenses
        );
        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Update an existing expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense updated successfully"),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<ExpenseDto>> updateExpense(
            @Parameter(description = "ID of the expense to update") @PathVariable Long id,
            @Parameter(description = "Updated expense data") @Valid @RequestBody ExpenseDto expenseDTO) {
        log.info("Received request to update expense id={} with data {}", id, expenseDTO);
        ExpenseDto updatedExpense = expenseService.updateExpense(id, expenseDTO);
        log.info("Expense id={} updated successfully", id);


        ResponseWrapper<ExpenseDto> body = new ResponseWrapper<>(
                "Expense updated successfully",
                updatedExpense
        );
        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Delete an expense")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Expense deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Expense not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<?>> deleteExpense(
            @Parameter(description = "ID of the expense to delete") @PathVariable Long id) {
        log.info("Received request to delete expense id={}", id);
        expenseService.deleteExpense(id);
        log.info("Expense id={} deleted successfully", id);

        ResponseWrapper<?> body = new ResponseWrapper<>(
                "Expense deleted successfully",
                null
        );
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResponseWrapper<String>> handleExpenseException(ResponseStatusException ex) {
        log.warn("Handling exception: status={}, reason={}", ex.getStatusCode(), ex.getReason());
        ResponseWrapper<String> body = new ResponseWrapper<>(
                ex.getReason(),
                null
        );
        return ResponseEntity.status(ex.getStatusCode())
                .body(body);
    }
}
