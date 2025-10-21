package hhz.ktoeto.moneymanager.ui.feature.transaction.domain;

import hhz.ktoeto.moneymanager.ui.feature.category.domain.Category;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Transaction {

    public enum Type {
        INCOME, EXPENSE;


        @Override
        public String toString() {
            return switch (this) {
                case INCOME -> "Доход";
                case EXPENSE -> "Расход";
            };
        }
    }

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 7)
    private Type type = Type.EXPENSE;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "date", nullable = false)
    private LocalDate date = LocalDate.now();

    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(name = "description", length = 1000)
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}