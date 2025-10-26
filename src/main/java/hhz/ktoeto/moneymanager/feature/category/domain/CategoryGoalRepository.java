package hhz.ktoeto.moneymanager.feature.category.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryGoalRepository extends JpaRepository<CategoryGoal, Long> {

    Page<CategoryGoal> findByUserId(Long userId, Pageable pageable);
}
