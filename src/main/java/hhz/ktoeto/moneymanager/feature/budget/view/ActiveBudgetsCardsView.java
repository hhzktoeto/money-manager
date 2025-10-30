package hhz.ktoeto.moneymanager.feature.budget.view;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.component.BudgetCard;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;
import hhz.ktoeto.moneymanager.ui.mixin.CanAddToFavourite;
import hhz.ktoeto.moneymanager.ui.mixin.CanCreate;

import java.util.List;

public class ActiveBudgetsCardsView extends BudgetsCardsView {

    private final CanCreate canCreateDelegate;
    private final CanAddToFavourite<Budget> canAddToFavouriteDelegate;

    private final FlexLayout addNewBudgetButton;

    public ActiveBudgetsCardsView(ActiveBudgetsCardPresenter presenter) {
        super(presenter);
        this.canCreateDelegate = presenter;
        this.canAddToFavouriteDelegate = presenter;

        this.addNewBudgetButton = new FlexLayout(VaadinIcon.PLUS.create(), new Span("Новый бюджет"));
    }

    @Override
    protected Div initContent() {
        Div root = super.initContent();

        this.addNewBudgetButton.addClassNames(
                StyleConstants.CLICKABLE,
                LumoUtility.BorderRadius.LARGE,
                LumoUtility.Gap.SMALL,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.CENTER,
                LumoUtility.Background.TRANSPARENT,
                LumoUtility.Border.ALL,
                LumoUtility.BorderColor.PRIMARY_50,
                LumoUtility.TextColor.PRIMARY,
                LumoUtility.FontSize.MEDIUM,
                LumoUtility.FontWeight.BOLD
        );
        this.addNewBudgetButton.setMinHeight(3, Unit.REM);
        this.addNewBudgetButton.addClickListener(e -> this.canCreateDelegate.onCreateRequested());

        return root;
    }

    @Override
    public void update(List<BudgetCard> data) {
        Div root = this.getContent();
        root.removeAll();

        data.forEach(card -> {
            Budget budget = card.getBudget();
            card.addContentClickListener(event -> this.presenter.onEditRequested(budget));
            card.addFavouriteButtonClickListener(event -> this.canAddToFavouriteDelegate.onAddToFavourites(budget));
            root.add(card);
        });
        root.add(this.addNewBudgetButton);
    }
}
