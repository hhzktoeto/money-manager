package hhz.ktoeto.moneymanager.feature.category.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByUserId(long userId);

    boolean existsByNameAndUserId(String name, long userId);
}
