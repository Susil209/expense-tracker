package com.expense.tracker.controller;

import com.expense.tracker.dto.CategoryDto;
import com.expense.tracker.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category", description = "CRUD operations for managing categories")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Create a new category
     * @param categoryDto Category data
     * @return Created category
     */
    @Operation(summary = "Create a new category", description = "Create a new category in the system")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
        return categoryService.saveCategory(categoryDto);
    }

    /**
     * Get all categories
     * @return List of categories
     */
    @Operation(summary = "Get all categories", description = "Retrieve a list of all categories")
    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories();
    }

    /**
     * Get category by ID
     * @param id ID of the category
     * @return Category object
     */
    @Operation(summary = "Get category by ID", description = "Retrieve a category by its ID")
    @GetMapping("/{id}")
    public CategoryDto getCategoryById(
            @Parameter(description = "ID of the category to be fetched", required = true)
            @PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    /**
     * Update a category
     * @param id ID of the category to update
     * @param categoryDto Updated category data
     * @return Updated category
     */
    @Operation(summary = "Update a category", description = "Update the details of an existing category")
    @PutMapping("/{id}")
    public CategoryDto updateCategory(
            @Parameter(description = "ID of the category to update", required = true)
            @PathVariable Long id,
            @RequestBody CategoryDto categoryDto) {
        return categoryService.updateCategory(id, categoryDto);
    }

    /**
     * Delete category by ID
     * @param id ID of the category to delete
     */
    @Operation(summary = "Delete a category", description = "Delete a category by its ID")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(
            @Parameter(description = "ID of the category to delete", required = true)
            @PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
