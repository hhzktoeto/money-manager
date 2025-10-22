package hhz.ktoeto.moneymanager.ui.feature.budget.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProviderListener;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.BudgetFilter;
import hhz.ktoeto.moneymanager.ui.feature.budget.event.OpenBudgetCreateDialogEvent;
import hhz.ktoeto.moneymanager.ui.feature.budget.event.OpenBudgetEditDialogEvent;
import hhz.ktoeto.moneymanager.ui.feature.budget.ui.data.BudgetsDataProvider;
import org.springframework.context.ApplicationEventPublisher;

@UIScope
@SpringComponent
public class ActiveBudgets extends Composite<Div> implements DataProviderListener<Budget> {

    private final BudgetsDataProvider dataProvider;
    private final FormattingService formattingService;
    private final ApplicationEventPublisher eventPublisher;

    private final FlexLayout addNewBudgetButton;

    public ActiveBudgets(BudgetsDataProvider dataProvider, FormattingService formattingService, ApplicationEventPublisher eventPublisher) {
        this.dataProvider = dataProvider;
        this.formattingService = formattingService;
        this.eventPublisher = eventPublisher;

        this.addNewBudgetButton = new FlexLayout(VaadinIcon.PLUS.create(), new Span("Новый бюджет"));

        dataProvider.addDataProviderListener(this);
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

        addNewBudgetButton.addClassNames(
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
        addNewBudgetButton.setMinHeight(3, Unit.REM);
        addNewBudgetButton.addClickListener(e -> eventPublisher.publishEvent(new OpenBudgetCreateDialogEvent(this)));

        root.addAttachListener(event -> this.updateCards());

        return root;
    }

    @Override
    public void onDataChange(DataChangeEvent<Budget> dataChangeEvent) {
        UI.getCurrent().access(this::updateCards);
    }

    private void updateCards() {
        Div root = this.getContent();
        root.removeAll();
        Query<Budget, BudgetFilter> query = new Query<>(BudgetFilter.activeBudgetsFilter());
        dataProvider.fetch(query).forEach(budget -> {
                    BudgetCard card = new BudgetCard(budget, formattingService);
                    card.addClickListener(event ->
                            eventPublisher.publishEvent(new OpenBudgetEditDialogEvent(this, budget))
                    );
                    root.add(card);
                }
        );
        root.add(addNewBudgetButton);
    }
}
