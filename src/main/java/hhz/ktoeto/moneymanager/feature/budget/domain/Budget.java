package hhz.ktoeto.moneymanager.feature.budget.domain;

import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "budgets")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Budget {

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

    public enum Scope {
        ALL, BY_CATEGORIES;

        @Override
        public String toString() {
            return switch (this) {
                case ALL -> "Все";
                case BY_CATEGORIES -> "По категориям";
            };
        }
    }

    public enum ActivePeriod {
        DAY, WEEK, MONTH, QUARTER, YEAR;

        @Override
        public String toString() {
            return switch (this) {
                case DAY -> "День";
                case WEEK -> "Неделя";
                case MONTH -> "Месяц";
                case QUARTER -> "Квартал";
                case YEAR -> "Год";
            };
        }
    }

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false, length = 64)
    private String name;

    @Column(name = "is_renewable", nullable = false)
    private boolean isRenewable = true;

    @Column(name = "is_favourite", nullable = false)
    private boolean isFavourite = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 7)
    private Type type = Type.EXPENSE;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope", nullable = false, length = 13)
    private Scope scope = Scope.ALL;

    @Enumerated(EnumType.STRING)
    @Column(name = "active_period", length = 7)
    private ActivePeriod activePeriod = ActivePeriod.MONTH;

    @Column(name = "goal_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal goalAmount;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "budget_categories",
            joinColumns = @JoinColumn(name = "budget_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Transient
    private BigDecimal currentAmount;

    @Transient
    public BigDecimal getRemainingAmount(){
        return goalAmount.subtract(currentAmount);
    }

    @Transient
    public void calculateActiveDates() {
        LocalDate today = LocalDate.now();
        LocalDate startDate;
        LocalDate endDate;

        switch (activePeriod) {
            case DAY -> {
                startDate = today;
                endDate = today;
            }
            case WEEK -> {
                startDate = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                endDate = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
            }
            case MONTH -> {
                startDate = today.with(TemporalAdjusters.firstDayOfMonth());
                endDate = today.with(TemporalAdjusters.lastDayOfMonth());
            }
            case QUARTER -> {
                int month = today.getMonthValue();
                int quarterStartMonth = ((month - 1) / 3) * 3 + 1;
                startDate = today.withMonth(quarterStartMonth).with(TemporalAdjusters.firstDayOfMonth());
                endDate = startDate.plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
            }
            case YEAR -> {
                startDate = today.with(TemporalAdjusters.firstDayOfYear());
                endDate = today.with(TemporalAdjusters.lastDayOfYear());
            }
            default -> throw new IllegalStateException("Unexpected value on budget active period: " + activePeriod);
        }

        this.startDate = startDate;
        this.endDate = endDate;
    }
}
