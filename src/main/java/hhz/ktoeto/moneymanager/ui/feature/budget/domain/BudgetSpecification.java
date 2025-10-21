package hhz.ktoeto.moneymanager.ui.feature.budget.domain;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Builder
public class BudgetSpecification implements Specification<Budget> {

    private final long userId;
    @Nullable
    private final BudgetFilter filter;

    @Override
    public Predicate toPredicate(Root<Budget> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.equal(root.get("userId"), userId);

        if (filter == null) {
            return predicate;
        }

        if (filter.isActive()) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), LocalDate.now()));
        }

        return predicate;
    }
}
