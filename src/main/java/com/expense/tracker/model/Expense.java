package com.expense.tracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenses",
        indexes = @Index(name = "idx_expense_date", columnList = "expense_date"),
        uniqueConstraints = @UniqueConstraint(columnNames = "description"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal amount;

    @Column(name = "description", length = 100, nullable = false)
    private String description;

    @Column(name = "expense_date",columnDefinition = "DATE", nullable = false)
    private LocalDate date;

    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinColumn(
            name = "category_id",
            foreignKey = @ForeignKey(name = "fk_expense_category"),
            nullable = true
    )
    private Category category;

}
