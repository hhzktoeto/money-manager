package hhz.ktoeto.moneymanager.feature.category.domain;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@Builder
public class CategorySpecification implements Specification<Category> {

    @Nullable
    private final Long userId;
    @Nullable
    private final transient CategoryFilter filter;

    @Override
    public Predicate toPredicate(@NonNull Root<Category> root, @NonNull CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();

        if (userId != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("userId"), userId));
        }

        if (filter == null) {
            return predicate;
        }

        if (filter.getName() != null && !filter.getName().isBlank()) {
            String searchPattern = "%" + filter.getName().toLowerCase() + "%";
            predicate = criteriaBuilder.and(predicate,
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("name")),
                            searchPattern
                    )
            );
        }

        return predicate;
    }
}
