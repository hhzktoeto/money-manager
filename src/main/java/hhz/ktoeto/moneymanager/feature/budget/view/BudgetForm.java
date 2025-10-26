package hhz.ktoeto.moneymanager.feature.budget.view;

import com.vaadin.componentfactory.DateRange;
import com.vaadin.flow.component.Component;
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
import hhz.ktoeto.moneymanager.core.constant.FormMode;
import hhz.ktoeto.moneymanager.feature.budget.BudgetFormView;
import hhz.ktoeto.moneymanager.feature.budget.BudgetFormViewPresenter;
import hhz.ktoeto.moneymanager.ui.component.AmountInputCalculator;
import hhz.ktoeto.moneymanager.ui.component.IncomeExpenseToggle;
import hhz.ktoeto.moneymanager.ui.component.RussianDateRangePicker;
import hhz.ktoeto.moneymanager.ui.component.ToggleButtonGroup;
import hhz.ktoeto.moneymanager.feature.budget.domain.Budget;
import hhz.ktoeto.moneymanager.feature.budget.validator.BudgetActivePeriodValidator;
import hhz.ktoeto.moneymanager.feature.budget.validator.BudgetCategoriesValidator;
import hhz.ktoeto.moneymanager.feature.budget.validator.BudgetDateRangeValidator;
import hhz.ktoeto.moneymanager.feature.budget.validator.BudgetNameValidator;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.category.ui.data.CategoryDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.vaadin.addons.gl0b3.materialicons.MaterialIcons;

@Slf4j
public class BudgetForm extends Composite<FlexLayout> implements BudgetFormView {

    private final transient BudgetFormViewPresenter presenter;
    private final CategoryDataProvider categoryProvider;
    private final FormMode mode;

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
    private final Button deleteButton;

    private final Binder<Budget> binder;

    private Budget.ActivePeriod previousActivePeriod;

    public BudgetForm(CategoryDataProvider categoryProvider, BudgetFormViewPresenter presenter, FormMode mode) {
        this.categoryProvider = categoryProvider;
        this.mode = mode;
        this.presenter = presenter;
        this.presenter.setView(this);

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
        this.deleteButton = new Button(MaterialIcons.DELETE.create());

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

        FlexLayout firstRowLayout = new FlexLayout();
        FlexLayout secondRowLayout = new FlexLayout();
        HorizontalLayout buttonsRow = new HorizontalLayout();

        this.configureNameField();
        this.configureFirstRow(firstRowLayout);
        this.configureSecondRow(secondRowLayout);
        this.configureButtonsRow(buttonsRow);
        this.configureBinder();

        root.add(
                typeToggle,
                nameField,
                firstRowLayout,
                secondRowLayout,
                amountInputCalculator,
                buttonsRow
        );

        return root;
    }

    @Override
    public boolean isCreateMode() {
        return mode == FormMode.CREATE;
    }

    @Override
    public void setEditedEntity(Budget budget) {
        binder.setBean(budget);
    }

    @Override
    public Budget getEditedEntity() {
        return binder.getBean();
    }

    @Override
    public void reset(Budget resetBudget) {
        binder.setBean(resetBudget);
    }

    @Override
    public boolean writeToIfValid(Budget budget) {
        try {
            binder.writeBean(budget);
            return true;
        } catch (ValidationException e) {
            log.error("Failed to validate budget");
            log.error(e.getValidationErrors().toString());
            return false;
        }
    }

    @Override
    public Component asComponent() {
        return this;
    }

    private void configureNameField() {
        nameField.setWidthFull();
    }

    private void configureFirstRow(FlexLayout row) {
        categoriesSelect.setItems(categoryProvider);
        categoriesSelect.setItemLabelGenerator(Category::getName);
        categoriesSelect.setWidthFull();

        createCategoryButton.setTooltipText("Добавить категорию");

        HorizontalLayout categoriesWrapper = new HorizontalLayout(categoriesSelect, createCategoryButton);
        categoriesWrapper.setVisible(false);
        categoriesWrapper.setPadding(false);
        categoriesWrapper.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.END,
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

        row.addClassNames(
                LumoUtility.Gap.SMALL,
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.FlexDirection.Breakpoint.Small.ROW
        );
        row.add(scopeToggle, categoriesWrapper);
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

    private void configureButtonsRow(HorizontalLayout row) {
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickListener(event -> presenter.onSubmit());

        cancelButton.addClickListener(event -> presenter.onCancel());

        deleteButton.addClickListener(event -> presenter.onDelete());
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
        deleteButton.setVisible(mode == FormMode.EDIT);

        HorizontalLayout submitCancelLayout = new HorizontalLayout(cancelButton, submitButton);
        submitCancelLayout.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.JustifyContent.END,
                LumoUtility.Gap.MEDIUM
        );

        row.add(deleteButton, submitCancelLayout);
        row.addClassNames(
                LumoUtility.AlignItems.STRETCH,
                LumoUtility.Margin.Top.MEDIUM,
                LumoUtility.JustifyContent.BETWEEN
        );
    }

    private void configureBinder() {
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
                .withValidator(new BudgetActivePeriodValidator(renewableCheckbox))
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
