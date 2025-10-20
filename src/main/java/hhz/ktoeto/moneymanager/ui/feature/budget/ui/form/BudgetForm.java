package hhz.ktoeto.moneymanager.ui.feature.budget.ui.form;

import com.vaadin.componentfactory.DateRange;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.component.AmountInputCalculator;
import hhz.ktoeto.moneymanager.ui.component.IncomeExpenseToggle;
import hhz.ktoeto.moneymanager.ui.component.RussianDateRangePicker;
import hhz.ktoeto.moneymanager.ui.component.ToggleButtonGroup;
import hhz.ktoeto.moneymanager.ui.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.ui.feature.budget.ui.form.validator.BudgetCategoriesValidator;
import hhz.ktoeto.moneymanager.ui.feature.budget.ui.form.validator.BudgetDateRangeValidator;
import hhz.ktoeto.moneymanager.ui.feature.budget.ui.form.validator.BudgetNameValidator;
import hhz.ktoeto.moneymanager.ui.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.ui.feature.category.ui.data.CategoryDataProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class BudgetForm extends Composite<FlexLayout> {

    private final CategoryDataProvider categoryProvider;
    private final transient Consumer<BudgetForm> categoryAddAction;
    private final transient Consumer<BudgetForm> submitAction;
    private final transient Consumer<BudgetForm> cancelAction;

    private final IncomeExpenseToggle<Budget.Type> typeToggle;
    private final TextField nameField;
    private final ToggleButtonGroup<Budget.Scope> scopeToggle;
    private final MultiSelectComboBox<Category> categoriesSelect;
    private final Button createCategoryButton;
    private final Checkbox renewableCheckbox;
    private final ToggleButtonGroup<Budget.ActivePeriod> activePeriodToggle;
    private final RussianDateRangePicker dateRangePicker;
    private final AmountInputCalculator amountInputCalculator;

    private final Button submitButton;
    private final Button cancelButton;

    private final Binder<Budget> binder;

    private Budget.ActivePeriod previousActivePeriod;

    public BudgetForm(CategoryDataProvider categoryProvider,
                      Consumer<BudgetForm> categoryAddAction,
                      Consumer<BudgetForm> submitAction,
                      Consumer<BudgetForm> cancelAction) {
        this.categoryProvider = categoryProvider;
        this.categoryAddAction = categoryAddAction;
        this.submitAction = submitAction;
        this.cancelAction = cancelAction;

        this.typeToggle = new IncomeExpenseToggle<>(Budget.Type.EXPENSE, Budget.Type.INCOME);
        this.nameField = new TextField("Название");
        this.scopeToggle = new ToggleButtonGroup<>("Учитываемые транзакции");
        this.categoriesSelect = new MultiSelectComboBox<>("Выберите категории");
        this.createCategoryButton = new Button(VaadinIcon.PLUS.create());
        this.renewableCheckbox = new Checkbox("Обновлять автоматически", true);
        this.activePeriodToggle = new ToggleButtonGroup<>();
        this.dateRangePicker = new RussianDateRangePicker("Период активности бюджета");
        this.amountInputCalculator = new AmountInputCalculator();
        this.submitButton = new Button("Сохранить");
        this.cancelButton = new Button("Отмена");
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

        nameField.setWidthFull();

        FlexLayout firstRowLayout = this.configureFirstRow();
        FlexLayout secondRowLayout = this.configureSecondRow();
        HorizontalLayout buttonsLayout = this.configureButtonsLayout();
        this.configureBinder();


        root.add(
                typeToggle,
                nameField,
                firstRowLayout,
                secondRowLayout,
                amountInputCalculator,
                buttonsLayout
        );

        return root;
    }

    boolean writeToIfValid(Budget budget) {
        try {
            binder.writeBean(budget);
            return true;
        } catch (ValidationException e) {
            log.error("Failed to validate budget");
            log.error(e.getValidationErrors().toString());
            return false;
        }
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
                        previousActivePeriod = binder.getBean().getActivePeriod();
                        activePeriodToggle.setValue(null);
                    }
                }
        );

        FlexLayout row = new FlexLayout(renewableCheckbox, activePeriodScroller, dateRangePicker);
        row.addClassNames(
                LumoUtility.Gap.SMALL,
                LumoUtility.FlexDirection.COLUMN
        );

        return row;
    }

    private HorizontalLayout configureButtonsLayout() {
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickListener(event -> this.submitAction.accept(this));

        cancelButton.addClickListener(event -> this.cancelAction.accept(this));

        HorizontalLayout layout = new HorizontalLayout();
        layout.add(cancelButton, submitButton);
        layout.addClassNames(
                LumoUtility.AlignItems.STRETCH,
                LumoUtility.Margin.Top.MEDIUM,
                LumoUtility.JustifyContent.BETWEEN,
                LumoUtility.Gap.LARGE
        );

        return layout;
    }

    private void configureBinder() {
        binder.setBean(new Budget());

        binder.forField(nameField)
                .withValidator(new BudgetNameValidator())
                .bind(Budget::getName, Budget::setName);
        binder.forField(typeToggle)
                .asRequired("Не выбран тип")
                .bind(Budget::getType, Budget::setType);
        binder.forField(scopeToggle)
                .asRequired("Не выбраны учитываемые транзакции")
                .bind(Budget::getScope, Budget::setScope);
        binder.forField(categoriesSelect)
                .withValidator(new BudgetCategoriesValidator(scopeToggle))
                .bind(Budget::getCategories, Budget::setCategories);
        binder.forField(renewableCheckbox)
                .bind(Budget::isRenewable, Budget::setRenewable);
        binder.forField(activePeriodToggle)
                .bind(Budget::getActivePeriod, Budget::setActivePeriod);
        binder.forField(dateRangePicker)
                .withValidator(new BudgetDateRangeValidator(renewableCheckbox))
                .bind(budget -> new DateRange(budget.getStartDate(), budget.getEndDate()),
                        (budget, dateRange) -> {
                            budget.setStartDate(dateRange.getStartDate());
                            budget.setEndDate(dateRange.getEndDate());
                        });
        binder.forField(amountInputCalculator)
                .asRequired("Не введена сумма")
                .bind(Budget::getGoalAmount, Budget::setGoalAmount);
    }
}
