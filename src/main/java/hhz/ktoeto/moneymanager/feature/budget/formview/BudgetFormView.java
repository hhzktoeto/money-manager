package hhz.ktoeto.moneymanager.feature.budget.formview;

import com.vaadin.componentfactory.DateRange;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.formview.validator.*;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.ui.AbstractFormView;
import hhz.ktoeto.moneymanager.ui.component.AmountInputCalculator;
import hhz.ktoeto.moneymanager.ui.component.IncomeExpenseToggle;
import hhz.ktoeto.moneymanager.ui.component.RussianDateRangePicker;
import hhz.ktoeto.moneymanager.ui.component.ToggleButtonGroup;
import hhz.ktoeto.moneymanager.ui.mixin.CanAddCategory;

public abstract class BudgetFormView extends AbstractFormView<Budget> {

    private final CanAddCategory categoryAddDelegate;
    private final CategoryDataProvider categoryProvider;

    private final IncomeExpenseToggle<Budget.Type> typeToggle;
    private final TextField nameField;
    private final ToggleButtonGroup<Budget.Scope> scopeToggle;
    private final MultiSelectComboBox<Category> categoriesSelect;
    private final Button createCategoryButton;
    private final Checkbox renewableCheckbox;
    private final ToggleButtonGroup<Budget.ActivePeriod> activePeriodToggle;
    private final RussianDateRangePicker dateRangePicker;
    private final AmountInputCalculator amountInputCalculator;

    private Budget.ActivePeriod previousActivePeriod;

    protected BudgetFormView(BudgetFormPresenter presenter, CategoryDataProvider categoryProvider) {
        super(presenter, Budget.class);
        this.categoryAddDelegate = presenter;
        this.categoryProvider = categoryProvider;

        this.typeToggle = new IncomeExpenseToggle<>(Budget.Type.EXPENSE, Budget.Type.INCOME);
        this.nameField = new TextField("Название");
        this.scopeToggle = new ToggleButtonGroup<>("Учитываемые транзакции");
        this.categoriesSelect = new MultiSelectComboBox<>("Выберите категории");
        this.createCategoryButton = new Button(VaadinIcon.PLUS.create());
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
                .withValidator(new BudgetNameValidator())
                .bind(Budget::getName, Budget::setName);
        binder.forField(this.typeToggle)
                .asRequired("Не выбран тип")
                .bind(Budget::getType, Budget::setType);
        binder.forField(this.scopeToggle)
                .asRequired("Не выбраны учитываемые транзакции")
                .bind(Budget::getScope, Budget::setScope);
        binder.forField(this.categoriesSelect)
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
                .withValidator(new BudgetAmountValidator())
                .bind(Budget::getGoalAmount, Budget::setGoalAmount);
    }

    private void configureFirstRow(FlexLayout row) {
        this.categoriesSelect.setItems(categoryProvider);
        this.categoriesSelect.setItemLabelGenerator(Category::getName);
        this.categoriesSelect.setWidthFull();

        this.createCategoryButton.addClickListener(event -> this.categoryAddDelegate.onCategoryAdd());
        this.createCategoryButton.setTooltipText("Добавить категорию");

        HorizontalLayout categoriesWrapper = new HorizontalLayout(this.categoriesSelect, this.createCategoryButton);
        categoriesWrapper.setVisible(false);
        categoriesWrapper.setPadding(false);
        categoriesWrapper.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.END,
                LumoUtility.JustifyContent.BETWEEN,
                LumoUtility.Gap.XSMALL
        );

        this.scopeToggle.setItems(Budget.Scope.values());
        this.scopeToggle.setValue(Budget.Scope.ALL);
        this.scopeToggle.setItemLabelGenerator(Budget.Scope::toString);
        this.scopeToggle.setToggleable(false);
        this.scopeToggle.addValueChangeListener(event ->
                categoriesWrapper.setVisible(event.getValue() == Budget.Scope.BY_CATEGORIES)
        );

        row.addClassNames(
                LumoUtility.Gap.SMALL,
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.FlexDirection.Breakpoint.Small.ROW
        );
        row.add(this.scopeToggle, categoriesWrapper);
    }

    private void configureSecondRow(FlexLayout row) {
        activePeriodToggle.setItems(Budget.ActivePeriod.values());
        activePeriodToggle.setValue(Budget.ActivePeriod.MONTH);
        activePeriodToggle.setItemLabelGenerator(Budget.ActivePeriod::toString);
        activePeriodToggle.setToggleable(false);

        dateRangePicker.setVisible(false);

        Scroller activePeriodScroller = new Scroller(activePeriodToggle);
        activePeriodScroller.setScrollDirection(Scroller.ScrollDirection.HORIZONTAL);

        renewableCheckbox.addValueChangeListener(event -> {
                    boolean autoRenew = event.getValue();
                    activePeriodScroller.setVisible(autoRenew);
                    dateRangePicker.setVisible(!autoRenew);
                    dateRangePicker.suppressKeyboard();

                    if (autoRenew) {
                        activePeriodToggle.setValue(previousActivePeriod);
                    } else {
                        previousActivePeriod = activePeriodToggle.getValue();
                        activePeriodToggle.setValue(null);
                    }
                }
        );

        row.addClassNames(
                LumoUtility.Gap.SMALL,
                LumoUtility.FlexDirection.COLUMN
        );
        row.add(renewableCheckbox, activePeriodScroller, dateRangePicker);
    }
}
