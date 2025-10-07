package hhz.ktoeto.moneymanager.backend.repository;

import hhz.ktoeto.moneymanager.backend.entity.BudgetGoal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetGoalRepository extends JpaRepository<BudgetGoal, Long> {

    Page<BudgetGoal> findByUserId(Long userId, Pageable pageable);
}
