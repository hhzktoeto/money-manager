package hhz.ktoeto.moneymanager.feature.category.domain;

import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "categories")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Category {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @ToString.Exclude
    @OneToMany(mappedBy = "category")
    private Set<CategoryGoal> goals = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "category")
    private Set<Transaction> transactions = new HashSet<>();

    @ToString.Exclude
    @ManyToMany(mappedBy = "categories")
    private Set<Budget> budgets = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Transient
    @ToString.Exclude
    private int transactionsCount;

    @Transient
    @ToString.Exclude
    private int expenseTransactionsCount;

    @Transient
    @ToString.Exclude
    private int incomeTransactionsCount;
}