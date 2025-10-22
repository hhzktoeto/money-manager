package hhz.ktoeto.moneymanager.ui.feature.transaction.ui.form;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.component.AmountInputCalculator;
import hhz.ktoeto.moneymanager.ui.component.IncomeExpenseToggle;
import hhz.ktoeto.moneymanager.ui.component.RussianDatePicker;
import hhz.ktoeto.moneymanager.ui.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.ui.feature.category.ui.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.ui.feature.transaction.ui.form.validator.TransactionAmountValidator;
import hhz.ktoeto.moneymanager.ui.feature.transaction.ui.form.validator.TransactionDescriptionValidator;
import lombok.extern.slf4j.Slf4j;
import org.vaadin.addons.gl0b3.materialicons.MaterialIcons;

import java.time.LocalDate;
import java.util.function.Consumer;

@Slf4j
public class TransactionForm extends Composite<FlexLayout> {

    private final CategoryDataProvider categoryProvider;
    private final transient Consumer<TransactionForm> categoryAddAction;
    private final transient Consumer<TransactionForm> submitAction;
    private final transient Consumer<TransactionForm> cancelAction;
    private final transient Consumer<TransactionForm> deleteAction;

    private final IncomeExpenseToggle<Transaction.Type> typeToggle;
    private final ComboBox<Category> categorySelect;
    private final AmountInputCalculator amountInput;
    private final DatePicker datePicker;
    private final TextArea descriptionArea;
    private final Button createCategoryButton;
    private final Button submitButton;
    private final Button cancelButton;
    private final Button deleteButton;

    private final Binder<Transaction> binder;

    public TransactionForm(CategoryDataProvider categoryProvider,
                           Consumer<TransactionForm> categoryAddAction,
                           Consumer<TransactionForm> submitAction,
                           Consumer<TransactionForm> cancelAction,
                           Consumer<TransactionForm> deleteAction) {
        this.categoryProvider = categoryProvider;
        this.categoryAddAction = categoryAddAction;
        this.submitAction = submitAction;
        this.cancelAction = cancelAction;
        this.deleteAction = deleteAction;

        this.typeToggle = new IncomeExpenseToggle<>(Transaction.Type.EXPENSE, Transaction.Type.INCOME);
        this.categorySelect = new ComboBox<>("Категория");
        this.amountInput = new AmountInputCalculator();
        this.datePicker = new RussianDatePicker("Дата", LocalDate.now());
        this.descriptionArea = new TextArea("Описание");
        this.createCategoryButton = new Button(VaadinIcon.PLUS.create());
        this.submitButton = new Button("Сохранить");
        this.cancelButton = new Button("Отмена");
        this.deleteButton = new Button(MaterialIcons.DELETE.create());

        this.binder = new Binder<>(Transaction.class);
    }

    @Override
    protected FlexLayout initContent() {
        FlexLayout root = new FlexLayout();
        root.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        root.addClassNames(
                LumoUtility.Padding.NONE,
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.STRETCH
        );

        FlexLayout firstRow = new FlexLayout();
        FlexLayout secondRow = new FlexLayout();
        HorizontalLayout buttonsRow = new HorizontalLayout();

        this.configureFirstRow(firstRow);
        this.configureSecondRow(secondRow);
        this.configureDescriptionArea();
        this.configureButtonsRow(buttonsRow);

        root.add(
                firstRow,
                secondRow,
                descriptionArea,
                buttonsRow
        );

        this.configureBinder();

        return root;
    }

    public void edit(Transaction transaction) {
        binder.setBean(transaction);
    }

    Transaction getEditedTransaction() {
        return binder.getBean();
    }

    boolean writeToIfValid(Transaction transaction) {
        try {
            binder.writeBean(transaction);
            return true;
        } catch (ValidationException e) {
            log.error("Failed to validate the transaction");
            log.error(e.getValidationErrors().toString());
            return false;
        }
    }

    void reset(LocalDate date, Category category, Transaction.Type type) {
        Transaction reset = new Transaction();
        reset.setType(type);
        reset.setDate(date);
        reset.setCategory(category);

        binder.setBean(reset);
    }

    void showDeleteButton(boolean show) {
        deleteButton.setVisible(show);
    }

    private void configureFirstRow(FlexLayout row) {
        categorySelect.setItems(categoryProvider);
        categorySelect.setItemLabelGenerator(Category::getName);
        categorySelect.setWidthFull();

        createCategoryButton.addClickListener(e -> categoryAddAction.accept(this));
        createCategoryButton.setTooltipText("Добавить категорию");

        HorizontalLayout categoryWrapper = new HorizontalLayout(categorySelect, createCategoryButton);
        categoryWrapper.setPadding(false);
        categoryWrapper.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.BASELINE,
                LumoUtility.JustifyContent.BETWEEN,
                LumoUtility.Gap.XSMALL
        );

        row.add(typeToggle, categoryWrapper);
        row.addClassNames(
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.FlexDirection.Breakpoint.Small.ROW,
                LumoUtility.AlignItems.BASELINE,
                LumoUtility.Gap.XSMALL
        );
    }

    private void configureSecondRow(FlexLayout row) {
        amountInput.setWidthFull();
        datePicker.setWidthFull();

        row.add(amountInput, datePicker);
        row.addClassNames(
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.FlexDirection.Breakpoint.Small.ROW,
                LumoUtility.AlignItems.STRETCH,
                LumoUtility.Gap.XSMALL
        );
    }

    private void configureDescriptionArea() {
        descriptionArea.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignSelf.CENTER
        );
    }

    private void configureButtonsRow(HorizontalLayout row) {
        submitButton.addClickListener(e -> submitAction.accept(this));
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        cancelButton.addClickListener(e -> cancelAction.accept(this));

        deleteButton.addClickListener(e -> deleteAction.accept(this));
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);

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
        binder.forField(typeToggle)
                .asRequired("Не выбран тип")
                .bind(Transaction::getType, Transaction::setType);
        binder.forField(categorySelect)
                .asRequired("Не выбрана категория")
                .bind(Transaction::getCategory, Transaction::setCategory);
        binder.forField(amountInput)
                .asRequired("Не введена сумма")
                .withValidator(new TransactionAmountValidator())
                .bind(Transaction::getAmount, Transaction::setAmount);
        binder.forField(datePicker)
                .asRequired("Не выбрана дата")
                .bind(Transaction::getDate, Transaction::setDate);
        binder.forField(descriptionArea)
                .withValidator(new TransactionDescriptionValidator())
                .bind(Transaction::getDescription, Transaction::setDescription);
    }
}
