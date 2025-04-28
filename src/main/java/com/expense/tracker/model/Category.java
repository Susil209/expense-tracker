package com.expense.tracker.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "categories",
        uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50, unique = true, nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "category",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true
    )
    private List<Expense> expenses = new ArrayList<>();

    public void addExpense(Expense expense) {
        this.expenses.add(expense);
        expense.setCategory(this);
    }

    public void removeExpense(Expense expense) {
        this.expenses.remove(expense);
        expense.setCategory(null);
    }
}

