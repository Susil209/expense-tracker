package com.expense.tracker.service;

import com.expense.tracker.dto.ExpenseDto;
import com.expense.tracker.exception.ExpenseNotFoundException;
import com.expense.tracker.model.Category;
import com.expense.tracker.model.Expense;
import com.expense.tracker.repository.CategoryRepository;
import com.expense.tracker.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    /**
     * Save a new expense
     * @param expenseDto Expense data to be saved
     * @return Saved expense DTO
     */
    // Method to save a new expense
    public ExpenseDto saveExpense(ExpenseDto expenseDto){
        validateExpense(expenseDto);

        // Find the category before saving the expense
        Category category = categoryRepository.findById(expenseDto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Category with ID " + expenseDto.getCategoryId() + " does not exist"));

        Expense expense = modelMapper.map(expenseDto, Expense.class);
        expense.setCategory(category); // Set category before saving
        Expense savedExpense = expenseRepository.save(expense);

        return modelMapper.map(savedExpense, ExpenseDto.class);
    }

    /**
     * Update an existing expense
     * @param id ID of the expense to update
     * @param dto Updated expense data
     * @return Updated expense DTO
     */
    // Method to update an existing expense
    public ExpenseDto updateExpense(Long id,ExpenseDto dto){

        Expense e = expenseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Expense not found"));

        validateExpense(dto);

        // Manually map only the updatable fields
        e.setAmount(dto.getAmount());
        e.setDescription(dto.getDescription());
        e.setDate(dto.getDate());

        // If you allow category changes:
        Category c = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Category not found"));
        e.setCategory(c);

        Expense saved = expenseRepository.save(e);
        return convertEntityToDTO(saved);


//        Expense existingExpense = expenseRepository.findById(id)
//                .map(expense -> {
//                    validateExpense(expenseDto);
//                    modelMapper.map(expenseDto, expense);
//
//                    // Update category if necessary
//                    if (expenseDto.getCategoryId() != null) {
//                        Category category = categoryRepository.findById(expenseDto.getCategoryId())
//                                .orElseThrow(() -> new ExpenseNotFoundException(
//                                        "Category with ID " + expenseDto.getCategoryId() + " does not exist"));
//                        expense.setCategory(category);
//                    }
//                    return expenseRepository.save(expense);
//                })
//                .orElseThrow(() -> new ExpenseNotFoundException("Expense with ID " + id + " not found"));
//
//        return modelMapper.map(existingExpense, ExpenseDto.class);

    }

    /**
     * Delete an expense by ID
     * @param id ID of the expense to delete
     */
    // Method to delete an expense
    public void deleteExpense(Long id){

        if (!expenseRepository.existsById(id)) {
            throw new ExpenseNotFoundException(
                    "Expense with ID " + id + " not found");
        }
        expenseRepository.deleteById(id);
    }

    /**
     * Get all expenses
     * @return List of all expenses
     */
    // get all expenses
    public List<ExpenseDto> getAllExpenses(){
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream()
                .map(expense -> modelMapper.map(expense, ExpenseDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Get expenses by category ID
     * @param categoryId Category ID to filter expenses
     * @return List of expenses for the category
     */
    // Method to get expenses by category ID
    public List<ExpenseDto> getExpensesByCategoryId(Long categoryId) {
        List<Expense> expenses = expenseRepository.findByCategoryId(categoryId);
        return expenses.stream()
                .map(expense -> modelMapper.map(expense, ExpenseDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Get expenses within a date range
     * @param startDate Start date of the range
     * @param endDate End date of the range
     * @return List of expenses within the date range
     */
    // Method to get expenses within a date range
    public List<ExpenseDto> getExpensesByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Expense> expenses = expenseRepository.findByDateRange(startDate, endDate);
        return expenses.stream()
                .map(expense -> modelMapper.map(expense, ExpenseDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Get total expenses for a category
     * @param categoryId Category ID to calculate total expenses
     * @return Total amount spent in the category
     */
    // Method to get total expenses for a category
    public BigDecimal getTotalExpensesByCategoryId(Long categoryId) {
        return expenseRepository.getTotalExpensesByCategoryId(categoryId)
                .orElse(BigDecimal.ZERO);
    }

//     Convert Entity to DTO
    private ExpenseDto convertEntityToDTO(Expense expense){
        return ExpenseDto.builder()
                .id(expense.getId())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .date(expense.getDate())
                .categoryId(expense.getCategory().getId())
                .build();
    }

    /**
     * Validate expense data before saving
     * @param expenseDTO Expense data to validate
     */
    private void validateExpense(ExpenseDto expenseDTO) {
        if (expenseDTO.getAmount() == null || expenseDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Expense amount must be a positive number");
        }

        if (expenseDTO.getDate() == null || expenseDTO.getDate().isAfter(LocalDate.now())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Expense date cannot be in the future");
        }

        if (expenseDTO.getCategoryId() == null || expenseDTO.getCategoryId() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Category ID must be a valid positive number");
        }

        if (expenseDTO.getDescription() == null || expenseDTO.getDescription().isEmpty() || expenseDTO.getDescription().length() > 200) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Description must be between 1 and 200 characters");
        }

        // Check if the category exists
        categoryRepository.findById(expenseDTO.getCategoryId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Category with ID " + expenseDTO.getCategoryId() + " does not exist"));

        if (expenseDTO.getAmount().compareTo(new BigDecimal(10000)) > 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Expense amount exceeds maximum allowed value of 10,000");
        }
    }
}
