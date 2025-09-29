package hhz.ktoeto.moneymanager.transaction.repository;

import hhz.ktoeto.moneymanager.transaction.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByNameAndUserId(String name, long userId);

    List<Category> findAllByUserId(long userId);

    long countAllByUserId(long userId);
}
