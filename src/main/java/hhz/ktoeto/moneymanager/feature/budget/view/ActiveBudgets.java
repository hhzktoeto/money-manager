package hhz.ktoeto.moneymanager.feature.budget.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;
import hhz.ktoeto.moneymanager.feature.budget.ActiveBudgetsView;
import hhz.ktoeto.moneymanager.ui.component.BudgetCard;
import hhz.ktoeto.moneymanager.feature.budget.ActiveBudgetsViewPresenter;

import java.util.List;

@UIScope
@SpringComponent
public class ActiveBudgets extends Composite<Div> implements ActiveBudgetsView {

    private final transient ActiveBudgetsViewPresenter presenter;

    private final FlexLayout addNewBudgetButton = new FlexLayout(VaadinIcon.PLUS.create(), new Span("Новый бюджет"));

    public ActiveBudgets(ActiveBudgetsViewPresenter presenter) {
        this.presenter = presenter;
        this.presenter.setView(this);
    }

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

        root.addAttachListener(event -> this.presenter.initialize());

        return root;
    }

    @Override
    public void updateCards(List<BudgetCard> cards) {
        Div root = this.getContent();
        root.removeAll();
        cards.forEach(card -> {
            card.addClickListener(event -> this.presenter.onEditRequested(card.getBudget()));
            root.add(card);
        });
        root.add(this.addNewBudgetButton);
    }

    @Override
    public Component asComponent() {
        return this;
    }
}
