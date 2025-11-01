package hhz.ktoeto.moneymanager.feature.budget.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.View;
import hhz.ktoeto.moneymanager.ui.component.BudgetCard;
import hhz.ktoeto.moneymanager.ui.component.EmptyDataImage;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;
import hhz.ktoeto.moneymanager.ui.mixin.HasUpdatableData;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.List;

public abstract class BudgetsCardsView extends Composite<Div> implements View, HasUpdatableData<List<Budget>> {

    @Getter(AccessLevel.PROTECTED)
    private final transient BudgetsCardsPresenter presenter;

    private final FlexLayout addNewBudgetButton;

    protected BudgetsCardsView(BudgetsCardsPresenter presenter) {
        this.presenter = presenter;
        this.addNewBudgetButton = new FlexLayout(VaadinIcon.PLUS.create(), new Span("Новый бюджет"));
    }

    protected abstract String getEmptyStateText();

    protected abstract boolean isAddBudgetButtonVisible();

    protected abstract BudgetCard mapBudgetToCard(Budget budget);

    @Override
    protected Div initContent() {
        Div root = new Div();
        root.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Gap.MEDIUM,
                LumoUtility.Display.GRID,
                LumoUtility.Grid.FLOW_ROW,
                LumoUtility.Grid.Column.COLUMNS_1,
                LumoUtility.Grid.Breakpoint.Small.COLUMNS_2,
                LumoUtility.Grid.Breakpoint.Large.COLUMNS_3,
                LumoUtility.AlignContent.START,
                LumoUtility.AlignItems.STRETCH
        );

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
        this.addNewBudgetButton.addClickListener(e -> this.presenter.onCreateRequested());

        return root;
    }

    @Override
    public Component asComponent() {
        return this;
    }

    @Override
    public void update(List<Budget> budgets) {
        Div root = this.getContent();
        root.removeAll();

        this.addNewBudgetButton.setVisible(this.isAddBudgetButtonVisible());

        if (budgets.isEmpty()) {
            EmptyDataImage emptyDataImage = new EmptyDataImage();
            emptyDataImage.setText(this.getEmptyStateText());
            emptyDataImage.setImageMaxWidth(13, Unit.REM);
            root.add(emptyDataImage);
            root.add(addNewBudgetButton);
            return;
        }

        budgets.stream()
                .map(this::mapBudgetToCard)
                .forEach(root::add);
        root.add(addNewBudgetButton);
    }
}
