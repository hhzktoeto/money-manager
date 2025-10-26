package hhz.ktoeto.moneymanager.feature.transaction.domain;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.CollectionUtils;

@Builder
public class TransactionSpecification implements Specification<Transaction> {

    @Nullable
    private final Long userId;
    @Nullable
    private final transient TransactionFilter filter;

    @Override
    public Predicate toPredicate(@NonNull Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (userId != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("userId"), userId));
        }

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
        if (!CollectionUtils.isEmpty(filter.getCategoriesIds())) {
            predicate = criteriaBuilder.and(predicate,
                    root.get("category").get("id").in(filter.getCategoriesIds())
            );
        }
        if (filter.getType() != null) {
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.equal(root.get("type"), filter.getType())
            );
        }

        return predicate;
    }
}
