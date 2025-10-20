package hhz.ktoeto.moneymanager.ui.feature.transaction.domain;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

@Builder
public class TransactionSpecification implements Specification<Transaction> {

    private final long userId;
    private final TransactionFilter filter;

    @Override
    public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.equal(root.get("userId"), userId);

        if (filter == null) {
            return predicate;
        }

        if (filter.getFromDate() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.greaterThanOrEqualTo(root.get("date"), filter.getFromDate())
            );
        }
        if (filter.getToDate() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.lessThanOrEqualTo(root.get("date"), filter.getToDate())
            );
        }
        return predicate;
    }
}
