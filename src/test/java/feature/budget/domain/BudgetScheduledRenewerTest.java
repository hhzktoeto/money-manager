package feature.budget.domain;

import hhz.ktoeto.moneymanager.Launcher;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetRepository;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetScheduledRenewer;
import hhz.ktoeto.moneymanager.feature.budget.domain.BudgetSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@SpringBootTest(classes = Launcher.class, webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class BudgetScheduledRenewerTest {

    @Autowired
    private BudgetRepository repository;
    @Autowired
    private BudgetScheduledRenewer renewer;

    @PersistenceContext
    private EntityManager em;

    private final String testBudgetName = "Test budget";
    private final long testUserId = 1L;
    private final LocalDate testBudgetStartDate = LocalDate.now().minusMonths(1);
    private final LocalDate testBudgetExpiredEndDate = LocalDate.now().minusDays(1);
    private final LocalDate testBudgetActiveEndDate = LocalDate.now().plusDays(1);

    @Test
    void autoRenew_marksOldBudgetAsNotFavourite_andCreatesNew() {
        Budget expiredBudget = new Budget();
        expiredBudget.setName(testBudgetName);
        expiredBudget.setUserId(testUserId);
        expiredBudget.setRenewable(true);
        expiredBudget.setFavourite(true);
        expiredBudget.setGoalAmount(BigDecimal.TEN);
        expiredBudget.setStartDate(testBudgetStartDate);
        expiredBudget.setEndDate(testBudgetExpiredEndDate);

        repository.saveAndFlush(expiredBudget);
        em.clear();

        renewer.autoRenew();

        em.clear();
        Budget reloaded = repository.findById(expiredBudget.getId()).orElseThrow();
        Budget newBudget = repository.findById(2L).orElseThrow();

        assertAll(
                () -> assertFalse(reloaded.isFavourite()),
                () -> assertTrue(newBudget.isFavourite()),
                () -> assertTrue(newBudget.isRenewable()),
                () -> assertThat(repository.findAll(
                        BudgetSpecification.builder()
                                .userId(1L)
                                .build()
                )).hasSize(2),
                () -> assertEquals(reloaded.getName(), newBudget.getName()),
                () -> assertEquals(reloaded.isRenewable(), newBudget.isRenewable()),
                () -> assertNotEquals(reloaded.isFavourite(), newBudget.isFavourite()),
                () -> assertEquals(reloaded.getType(), newBudget.getType()),
                () -> assertEquals(reloaded.getScope(), newBudget.getScope()),
                () -> assertEquals(reloaded.getActivePeriod(), newBudget.getActivePeriod()),
                () -> assertEquals(reloaded.getGoalAmount(), newBudget.getGoalAmount()),
                () -> assertEquals(reloaded.getUserId(), newBudget.getUserId())
        );
    }
}
