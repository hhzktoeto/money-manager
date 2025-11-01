package hhz.ktoeto.moneymanager.feature.budget.view;

import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.ui.component.BudgetCard;

import java.math.BigDecimal;

public class ActiveBudgetsCardsView extends BudgetsCardsView {

    public ActiveBudgetsCardsView(ActiveBudgetsCardPresenter presenter) {
        super(presenter);
    }

    @Override
    protected String getEmptyStateText() {
        return "Нет активных бюджетов";
    }

    @Override
    protected boolean isAddBudgetButtonVisible() {
        return true;
    }

    @Override
    protected BudgetCard mapBudgetToCard(Budget budget) {
        BigDecimal currentAmount = budget.getTransactions()
                .stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal maxAmount = budget.getGoalAmount();

        BudgetCard.BudgetCardData budgetCardData = BudgetCard.BudgetCardData.builder()
                .currentAmount(currentAmount)
                .maxAmount(maxAmount)
                .remainingAmount(maxAmount.subtract(currentAmount))
                .endDate(budget.getEndDate())
                .title(budget.getName())
                .isFavourite(budget.isFavourite())
                .isExpense(Budget.Type.EXPENSE == budget.getType())
                .typeName(budget.getType().toString())
                .amountFormatter(this.getPresenter()::formatAmount)
                .dateFormatter(this.getPresenter()::formatDate)
                .build();

        BudgetCard card = new BudgetCard(budgetCardData);
        card.addContentClickListener(event -> this.getPresenter().onEditRequested(budget));
        card.addFavouriteButtonClickListener(event -> this.getPresenter().onAddToFavourites(budget));

        return card;
    }
}
