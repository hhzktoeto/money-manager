package hhz.ktoeto.moneymanager.ui.feature.category.domain;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Builder;
import org.springframework.data.jpa.domain.Specification;

@Builder
public class CategorySpecification implements Specification<Category> {

    private final long userId;
    private final CategoryFilter filter;

    @Override
    public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.equal(root.get("userId"), userId);

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
