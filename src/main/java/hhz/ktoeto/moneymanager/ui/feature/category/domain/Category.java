package hhz.ktoeto.moneymanager.ui.feature.category.domain;

import hhz.ktoeto.moneymanager.ui.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.Transaction;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @ToString.Exclude
    @OneToMany(mappedBy = "category")
    private List<CategoryGoal> goals;

    @ToString.Exclude
    @OneToMany(mappedBy = "category")
    private List<Transaction> transactions;

    @ToString.Exclude
    @ManyToMany(mappedBy = "categories")
    private List<Budget> budgets;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

}