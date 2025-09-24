package hhz.ktoeto.moneymanager.transaction.mapper;

import hhz.ktoeto.moneymanager.transaction.model.budgetgoal.BudgetGoal;
import hhz.ktoeto.moneymanager.transaction.model.budgetgoal.BudgetGoalDTO;
import hhz.ktoeto.moneymanager.transaction.model.category.Category;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BudgetGoalMapper {

    @Mapping(target = "category", source = "category", qualifiedByName = "mapCategory")
    @Mapping(target = "type", source = "type", qualifiedByName = "mapType")
    BudgetGoalDTO toDto(BudgetGoal budgetGoal);

    @Mapping(target = "category", ignore = true)
    BudgetGoal toEntity(BudgetGoalDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    void updateEntity(@MappingTarget BudgetGoal goal, BudgetGoalDTO goalDTO);

    @Named("mapCategory")
    default String mapCategory(Category category) {
        return category.getName();
    }

    @Named("mapType")
    default String mapType(BudgetGoal.Type type) {
        return type.name();
    }
}

