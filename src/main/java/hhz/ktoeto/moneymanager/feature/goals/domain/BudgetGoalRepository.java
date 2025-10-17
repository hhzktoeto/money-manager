package hhz.ktoeto.moneymanager.feature.goals.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetGoalRepository extends JpaRepository<BudgetGoal, Long> {

    Page<BudgetGoal> findByUserId(Long userId, Pageable pageable);
}
