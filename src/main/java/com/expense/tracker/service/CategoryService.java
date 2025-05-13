package com.expense.tracker.service;

import com.expense.tracker.dto.CategoryDto;
import com.expense.tracker.exception.CategoryNotFoundException;
import com.expense.tracker.model.Category;
import com.expense.tracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryDto saveCategory(CategoryDto categoryDto) {
        // Validation can be added here (e.g., name must not be null or empty)
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
    }

    public CategoryDto getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + " not found"));

        return new CategoryDto(category.getId(), category.getName());
    }

    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category existingCategory = categoryRepository.findById(id)
                .map(category -> {
                    category.setName(categoryDto.getName());
                    return categoryRepository.save(category);
                })
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + " not found"));
        return modelMapper.map(existingCategory, CategoryDto.class);
    }

    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category with ID " + id + " not found");
        }
        categoryRepository.deleteById(id);
    }
}
