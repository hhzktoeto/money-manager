package hhz.ktoeto.moneymanager.ui.feature.budget.ui.form;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.component.IncomeExpenseToggle;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.ui.feature.category.ui.data.CategoryDataProvider;
import lombok.RequiredArgsConstructor;
import org.vaadin.addons.taefi.component.ToggleButtonGroup;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class BudgetForm extends Composite<FlexLayout> {

    private final CategoryDataProvider categoryProvider;
    private final transient Consumer<BudgetForm> categoryAddAction;
    private final transient Consumer<BudgetForm> submitAction;
    private final transient Consumer<BudgetForm> cancelAction;

    private final IncomeExpenseToggle<Budget.Type> typeToggle;
    private final MultiSelectComboBox<Category> categoriesSelect;
    private final Button createCategoryButton;
    private final ToggleButtonGroup<Budget.Scope> scopeToggle;
    private final Checkbox renewableCheckbox;
    private final Select<Budget.ActivePeriod> activePeriodToggle;

    private final Binder<Budget> binder;

    public BudgetForm(CategoryDataProvider categoryProvider,
                      Consumer<BudgetForm> categoryAddAction,
                      Consumer<BudgetForm> submitAction,
                      Consumer<BudgetForm> cancelAction) {
        this.categoryProvider = categoryProvider;
        this.categoryAddAction = categoryAddAction;
        this.submitAction = submitAction;
        this.cancelAction = cancelAction;

        this.typeToggle = new IncomeExpenseToggle<>(Budget.Type.EXPENSE, Budget.Type.INCOME);
        this.categoriesSelect = new MultiSelectComboBox<>("Выберите категории");
        this.createCategoryButton = new Button(VaadinIcon.PLUS.create());
        this.scopeToggle = new ToggleButtonGroup<>("Учитываемые транзакции");
        this.renewableCheckbox = new Checkbox("Обновлять автоматически", true);
        this.activePeriodToggle = new Select<>();
        this.binder = new Binder<>(Budget.class);
    }

    @Override
    protected FlexLayout initContent() {
        FlexLayout root = new FlexLayout();
        root.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        root.addClassNames(
                LumoUtility.Padding.NONE,
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.STRETCH,
                LumoUtility.Gap.MEDIUM
        );

        FlexLayout firstRowLayout = this.configureFirstRow();
        FlexLayout secondRowLayout = this.configureSecondRow();

        root.add(
                typeToggle,
                firstRowLayout,
                secondRowLayout
        );

        return root;
    }

    private FlexLayout configureFirstRow() {
        categoriesSelect.setItems(categoryProvider);
        categoriesSelect.setItemLabelGenerator(Category::getName);
        categoriesSelect.setWidthFull();

        createCategoryButton.addClickListener(e -> categoryAddAction.accept(this));
        createCategoryButton.setTooltipText("Добавить категорию");

        HorizontalLayout categoriesWrapper = new HorizontalLayout(categoriesSelect, createCategoryButton);
        categoriesWrapper.setVisible(false);
        categoriesWrapper.setPadding(false);
        categoriesWrapper.setSpacing(false);
        categoriesWrapper.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.BASELINE,
                LumoUtility.JustifyContent.BETWEEN,
                LumoUtility.Gap.XSMALL
        );

        scopeToggle.setItems(Budget.Scope.values());
        scopeToggle.setValue(Budget.Scope.ALL);
        scopeToggle.setItemLabelGenerator(Budget.Scope::toString);
        scopeToggle.setToggleable(false);
        scopeToggle.addValueChangeListener(event ->
                categoriesWrapper.setVisible(event.getValue() == Budget.Scope.BY_CATEGORIES)
        );

        FlexLayout row = new FlexLayout(scopeToggle, categoriesWrapper);
        row.addClassNames(
                LumoUtility.Gap.SMALL,
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.FlexDirection.Breakpoint.Small.ROW
        );

        return row;
    }

    private FlexLayout configureSecondRow() {
        activePeriodToggle.setItems(Budget.ActivePeriod.values());
        activePeriodToggle.setValue(Budget.ActivePeriod.MONTH);
        activePeriodToggle.setItemLabelGenerator(Budget.ActivePeriod::toString);

        renewableCheckbox.addValueChangeListener(event ->
                activePeriodToggle.setVisible(event.getValue())
        );

        FlexLayout row = new FlexLayout(renewableCheckbox, activePeriodToggle);
        row.addClassNames(
                LumoUtility.Gap.SMALL,
                LumoUtility.FlexDirection.COLUMN
        );

        return row;
    }
}
