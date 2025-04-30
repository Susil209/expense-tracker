package com.expense.tracker.service;

import com.expense.tracker.dto.ExpenseDto;
import com.expense.tracker.exception.ExpenseNotFoundException;
import com.expense.tracker.model.Category;
import com.expense.tracker.model.Expense;
import com.expense.tracker.repository.CategoryRepository;
import com.expense.tracker.repository.ExpenseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {
    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @InjectMocks
    private ExpenseService expenseService;

    @Test
    void saveExpense_success() {
        // given
        ExpenseDto dto = new ExpenseDto(
                null,
                new BigDecimal("100.00"),
                "Groceries",
                LocalDate.of(2023, 7, 1),
                1L
        );

        Category cat = new Category();
        cat.setId(1L);
        cat.setName("Food");

        // service will call categoryRepository.findById(1L)
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(cat));

        // simulate the repository assigning ID=42 when saving
        Expense saved = new Expense();
        saved.setId(42L);
        saved.setAmount(dto.getAmount());
        saved.setDescription(dto.getDescription());
        saved.setDate(dto.getDate());
        saved.setCategory(cat);
        when(expenseRepository.save(any(Expense.class))).thenReturn(saved);

        // when
        ExpenseDto result = expenseService.saveExpense(dto);

        // then
        assertNotNull(result.getId(), "The returned DTO must have an ID");
        assertEquals(42L, result.getId());
        assertEquals(dto.getAmount(), result.getAmount());
        assertEquals(dto.getDescription(), result.getDescription());
        assertEquals(dto.getDate(), result.getDate());
        assertEquals(dto.getCategoryId(), result.getCategoryId());
    }

    @Test
    void saveExpense_invalidCategory_throws() {
        // given
        ExpenseDto dto = new ExpenseDto(
                null,
                new BigDecimal("50.00"),
                "Snacks",
                LocalDate.now(),
                99L    // non-existent
        );

        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        // when / then
        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> expenseService.saveExpense(dto));

        assertEquals("Category with ID "+ dto.getCategoryId() + " does not exist", ex.getReason());
        assertEquals(400, ex.getStatusCode().value());
    }

    @Test
    void getAllExpenses_returnsList() {
        // given
        Expense e1 = new Expense(1L, new BigDecimal("10.00"), "A", LocalDate.now(), null);
        Expense e2 = new Expense(2L, new BigDecimal("20.00"), "B", LocalDate.now(), null);
        when(expenseRepository.findAll()).thenReturn(Arrays.asList(e1, e2));

        // when
        List<ExpenseDto> result = expenseService.getAllExpenses();

        // then
        assertEquals(2, result.size());
        assertEquals("A", result.get(0).getDescription());
        assertEquals("B", result.get(1).getDescription());
    }

    @Test
    void getExpensesByCategoryId_returnsList() {
        // given
        Category cat = new Category(5L, "Cat", null);
        Expense e1 = new Expense(1L, new BigDecimal("15.00"), "X", LocalDate.now(), cat);
        when(expenseRepository.findByCategoryId(5L)).thenReturn(List.of(e1));

        // when
        List<ExpenseDto> result = expenseService.getExpensesByCategoryId(5L);

        // then
        assertEquals(1, result.size());
        assertEquals(5L, result.get(0).getCategoryId());
    }

    @Test
    void getExpensesByDateRange_returnsList() {
        // given
        LocalDate start = LocalDate.of(2023,1,1), end = LocalDate.of(2023,1,31);
        Expense e1 = new Expense(1L, new BigDecimal("30.00"), "Y", LocalDate.of(2023,1,15), null);
        when(expenseRepository.findByDateRange(start, end)).thenReturn(List.of(e1));

        // when
        List<ExpenseDto> result = expenseService.getExpensesByDateRange(start, end);

        // then
        assertEquals(1, result.size());
        assertEquals("Y", result.get(0).getDescription());
    }

    @Test
    void updateExpense_success() {
        // given
        Long id = 10L;
        ExpenseDto dto = new ExpenseDto(null, new BigDecimal("50.00"), "Updated", LocalDate.now(), 2L);
        Category cat = new Category(2L, "NewCat", null);
        Expense existing = new Expense(id, new BigDecimal("40.00"), "Old", LocalDate.now(), cat);

        when(expenseRepository.findById(id)).thenReturn(Optional.of(existing));
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(cat));
        when(expenseRepository.save(any(Expense.class))).thenAnswer(inv -> inv.getArgument(0));

        // when
        ExpenseDto updated = expenseService.updateExpense(id, dto);

        // then
        assertEquals(id, updated.getId());
        assertEquals("Updated", updated.getDescription());
        assertEquals(new BigDecimal("50.00"), updated.getAmount());
        verify(expenseRepository).save(existing);
    }

    @Test
    void updateExpense_notFound_throws() {
        // given
        when(expenseRepository.findById(99L)).thenReturn(Optional.empty());
        ExpenseDto dto = new ExpenseDto(null, BigDecimal.ONE, "Z", LocalDate.now(), 1L);

        // when / then
        ResponseStatusException ex = assertThrows(
                ResponseStatusException.class,
                () -> expenseService.updateExpense(99L, dto)
        );
        assertEquals("Expense not found", ex.getReason());
    }

    @Test
    void deleteExpense_success() {
        // given
        Long id = 20L;
        when(expenseRepository.existsById(id)).thenReturn(true);

        // when
        assertDoesNotThrow(() -> expenseService.deleteExpense(id));

        // then
        verify(expenseRepository).deleteById(id);
    }

    @Test
    void deleteExpense_notFound_throws() {
        // given
        Long id = 30L;
        when(expenseRepository.existsById(id)).thenReturn(false);

        // when / then
        ExpenseNotFoundException ex = assertThrows(
                ExpenseNotFoundException.class,
                () -> expenseService.deleteExpense(id)
        );
        assertEquals("Expense with ID " + id + " not found", ex.getMessage());
    }
}
