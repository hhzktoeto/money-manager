package hhz.ktoeto.moneymanager.transaction.repository;

import hhz.ktoeto.moneymanager.transaction.model.budgetgoal.BudgetGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BudgetGoalRepository extends JpaRepository<BudgetGoal, Long> {

    List<BudgetGoal> findAllByUserId(Long userId);
}
