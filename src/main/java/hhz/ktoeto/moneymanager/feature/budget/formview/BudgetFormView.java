package hhz.ktoeto.moneymanager.feature.budget.formview;

import com.vaadin.componentfactory.DateRange;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.formview.validator.*;
import hhz.ktoeto.moneymanager.feature.category.data.SimpleCategoriesProvider;
import hhz.ktoeto.moneymanager.ui.AbstractFormView;
import hhz.ktoeto.moneymanager.ui.component.field.*;
import hhz.ktoeto.moneymanager.ui.mixin.CanAddCategory;

public abstract class BudgetFormView extends AbstractFormView<Budget> {

    private final CanAddCategory categoryAddDelegate;
    private final SimpleCategoriesProvider categoryProvider;

    private final IncomeExpenseToggle<Budget.Type> typeToggle;
    private final TextField nameField;
    private final ToggleButtonGroup<Budget.Scope> scopeToggle;
    private final CategoryMultiselectField categoryMultiselectField;
    private final Checkbox renewableCheckbox;
    private final ToggleButtonGroup<Budget.ActivePeriod> activePeriodToggle;
    private final RussianDateRangePicker dateRangePicker;
    private final AmountInputCalculator amountInputCalculator;

    private Budget.ActivePeriod previousActivePeriod;

    protected BudgetFormView(BudgetFormPresenter presenter, SimpleCategoriesProvider categoryProvider) {
        super(presenter, Budget.class);
        this.categoryAddDelegate = presenter;
        this.categoryProvider = categoryProvider;

        this.typeToggle = new IncomeExpenseToggle<>(Budget.Type.EXPENSE, Budget.Type.INCOME);
        this.nameField = new TextField("Название");
        this.scopeToggle = new ToggleButtonGroup<>("Учитываемые транзакции");
        this.categoryMultiselectField = new CategoryMultiselectField();
        this.renewableCheckbox = new Checkbox("Обновлять автоматически", true);
        this.activePeriodToggle = new ToggleButtonGroup<>();
        this.dateRangePicker = new RussianDateRangePicker("Период активности бюджета");
        this.amountInputCalculator = new AmountInputCalculator();
    }

    @Override
    protected void configureRootContent(FlexLayout root) {
        FlexLayout firstRowLayout = new FlexLayout();
        FlexLayout secondRowLayout = new FlexLayout();

        this.nameField.setWidthFull();
        this.configureFirstRow(firstRowLayout);
        this.configureSecondRow(secondRowLayout);

        root.add(
                this.typeToggle,
                this.nameField,
                firstRowLayout,
                secondRowLayout,
                this.amountInputCalculator
        );
    }

    @Override
    protected void configureBinder(Binder<Budget> binder) {
        binder.forField(this.nameField)
                .asRequired()
                .withValidator(new BudgetNameValidator())
                .bind(Budget::getName, Budget::setName);
        binder.forField(this.typeToggle)
                .asRequired("Не выбран тип")
                .bind(Budget::getType, Budget::setType);
        binder.forField(this.scopeToggle)
                .asRequired("Не выбраны учитываемые транзакции")
                .bind(Budget::getScope, Budget::setScope);
        binder.forField(this.categoryMultiselectField)
                .withValidator(new BudgetCategoriesValidator(this.scopeToggle))
                .bind(Budget::getCategories, Budget::setCategories);
        binder.forField(this.renewableCheckbox)
                .bind(Budget::isRenewable, Budget::setRenewable);
        binder.forField(this.activePeriodToggle)
                .withValidator(new BudgetActivePeriodValidator(this.renewableCheckbox))
                .bind(Budget::getActivePeriod, Budget::setActivePeriod);
        binder.forField(this.dateRangePicker)
                .withValidator(new BudgetDateRangeValidator(this.renewableCheckbox))
                .bind(budget -> new DateRange(budget.getStartDate(), budget.getEndDate()),
                        (budget, dateRange) -> {
                            budget.setStartDate(dateRange.getStartDate());
                            budget.setEndDate(dateRange.getEndDate());
                        });
        binder.forField(this.amountInputCalculator)
                .asRequired()
                .withValidator(new BudgetAmountValidator())
                .bind(Budget::getGoalAmount, Budget::setGoalAmount);
    }

    private void configureFirstRow(FlexLayout row) {
        this.categoryMultiselectField.setItems(categoryProvider);
        this.categoryMultiselectField.addButtonClickListener(event -> this.categoryAddDelegate.onCategoryAdd());
        this.categoryMultiselectField.setVisible(false);

        this.scopeToggle.setItems(Budget.Scope.values());
        this.scopeToggle.setValue(Budget.Scope.ALL);
        this.scopeToggle.setItemLabelGenerator(Budget.Scope::toString);
        this.scopeToggle.setToggleable(false);
        this.scopeToggle.addValueChangeListener(event ->
                this.categoryMultiselectField.setVisible(event.getValue() == Budget.Scope.BY_CATEGORIES)
        );

        row.add(this.scopeToggle, this.categoryMultiselectField);
        row.addClassNames(
                LumoUtility.Gap.SMALL,
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.FlexDirection.Breakpoint.Small.ROW
        );
    }

    private void configureSecondRow(FlexLayout row) {
        this.activePeriodToggle.setItems(Budget.ActivePeriod.values());
        this.activePeriodToggle.setValue(Budget.ActivePeriod.MONTH);
        this.activePeriodToggle.setItemLabelGenerator(Budget.ActivePeriod::toString);
        this.activePeriodToggle.setToggleable(false);

        this.dateRangePicker.setVisible(false);
        this.dateRangePicker.setRequiredIndicatorVisible(true);

        Scroller activePeriodScroller = new Scroller(this.activePeriodToggle);
        activePeriodScroller.setScrollDirection(Scroller.ScrollDirection.HORIZONTAL);

        this.renewableCheckbox.addValueChangeListener(event -> {
                    boolean autoRenew = event.getValue();
                    activePeriodScroller.setVisible(autoRenew);
                    this.dateRangePicker.setVisible(!autoRenew);

                    if (autoRenew) {
                        this.activePeriodToggle.setValue(previousActivePeriod);
                    } else {
                        previousActivePeriod = this.activePeriodToggle.getValue();
                        this.activePeriodToggle.setValue(null);
                    }
                }
        );

        row.addClassNames(
                LumoUtility.Gap.SMALL,
                LumoUtility.FlexDirection.COLUMN
        );
        row.add(this.renewableCheckbox, activePeriodScroller, this.dateRangePicker);
    }
}
