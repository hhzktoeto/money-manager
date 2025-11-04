package hhz.ktoeto.moneymanager.feature.budget.domain;

import jakarta.persistence.criteria.*;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Builder
public class BudgetSpecification implements Specification<Budget> {

    @Nullable
    private final Long userId;
    @Nullable
    private final transient BudgetFilter filter;

    @Override
    public Predicate toPredicate(@NotNull Root<Budget> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (userId != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("userId"), userId));
        }

        if (filter == null) {
            return predicate;
        }

        if (filter.getIsActive() != null) {
            if (filter.getIsActive()) {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), LocalDate.now()));
            } else {
                predicate = criteriaBuilder.and(predicate,
                        criteriaBuilder.lessThan(root.get("endDate"), LocalDate.now())
                );
            }
        }
        if (filter.getIsRenewable() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("isRenewable"), filter.getIsRenewable())
            );
        }
        if (filter.getIsFavourite() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("isFavourite"), filter.getIsFavourite())
            );
        }
        if (filter.isWithCategories()) {
            root.fetch("categories", JoinType.LEFT);
            query.distinct(true);
        }

        return predicate;
    }
}
