package hhz.ktoeto.moneymanager.transaction.mapper;

import hhz.ktoeto.moneymanager.transaction.model.budgetgoal.BudgetGoal;
import hhz.ktoeto.moneymanager.transaction.model.budgetgoal.BudgetGoalDTO;
import hhz.ktoeto.moneymanager.transaction.model.category.Category;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BudgetGoalMapper {

    @Mapping(target = "category", ignore = true)
    BudgetGoal toEntity(BudgetGoalDTO dto);

    @Named("mapCategory")
    default String mapCategory(Category category) {
        return category.getName();
    }
}

