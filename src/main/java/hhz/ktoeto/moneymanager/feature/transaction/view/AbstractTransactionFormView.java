package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.flow.component.Component;
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
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionFormView;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionFormViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.view.validator.TransactionAmountValidator;
import hhz.ktoeto.moneymanager.feature.transaction.view.validator.TransactionDescriptionValidator;
import hhz.ktoeto.moneymanager.ui.component.AmountInputCalculator;
import hhz.ktoeto.moneymanager.ui.component.IncomeExpenseToggle;
import hhz.ktoeto.moneymanager.ui.component.RussianDatePicker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addons.gl0b3.materialicons.MaterialIcons;

import java.time.LocalDate;

public abstract class AbstractTransactionFormView extends Composite<FlexLayout> implements TransactionFormView {

    protected final transient TransactionFormViewPresenter presenter;
    protected final CategoryDataProvider categoryProvider;
    protected final Binder<Transaction> binder;

    private final IncomeExpenseToggle<Transaction.Type> typeToggle;
    private final ComboBox<Category> categorySelect;
    private final AmountInputCalculator amountInput;
    private final DatePicker datePicker;
    private final TextArea descriptionArea;
    private final Button createCategoryButton;
    private final Button submitButton;
    private final Button cancelButton;
    private final Button deleteButton;


    private final Logger log;

    protected AbstractTransactionFormView(CategoryDataProvider categoryProvider, TransactionFormViewPresenter presenter) {
        this.categoryProvider = categoryProvider;
        this.presenter = presenter;
        this.binder = new Binder<>(Transaction.class);

        this.typeToggle = new IncomeExpenseToggle<>(Transaction.Type.EXPENSE, Transaction.Type.INCOME);
        this.categorySelect = new ComboBox<>("Категория");
        this.amountInput = new AmountInputCalculator();
        this.datePicker = new RussianDatePicker("Дата", LocalDate.now());
        this.descriptionArea = new TextArea("Описание");
        this.createCategoryButton = new Button(VaadinIcon.PLUS.create());
        this.submitButton = new Button("Сохранить");
        this.cancelButton = new Button("Отмена");
        this.deleteButton = new Button(MaterialIcons.DELETE.create());

        this.log = LoggerFactory.getLogger(this.getClass());

        this.presenter.initialize(this);
    }

    protected abstract boolean isDeleteButtonVisible();

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

    @Override
    public boolean writeToIfValid(Transaction transaction) {
        try {
            binder.writeBean(transaction);
            return true;
        } catch (ValidationException e) {
            log.error("Failed to validate the transaction");
            log.error(e.getValidationErrors().toString());
            return false;
        }
    }

    @Override
    public void setEntity(Transaction entity) {
        this.binder.setBean(entity);
    }

    @Override
    public Transaction getEntity() {
        return this.binder.getBean();
    }

    @Override
    public Component asComponent() {
        return this;
    }

    private void configureFirstRow(FlexLayout row) {
        categorySelect.setItems(categoryProvider);
        categorySelect.setItemLabelGenerator(Category::getName);
        categorySelect.setWidthFull();

        createCategoryButton.addClickListener(event -> presenter.onCategoryAdd());
        createCategoryButton.setHeight(categorySelect.getHeight());
        createCategoryButton.setTooltipText("Добавить категорию");

        HorizontalLayout categoryWrapper = new HorizontalLayout(categorySelect, createCategoryButton);
        categoryWrapper.setPadding(false);
        categoryWrapper.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.END,
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
        submitButton.addClickListener(e -> presenter.onSubmit());
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        cancelButton.addClickListener(e -> presenter.onCancel());

        deleteButton.addClickListener(e -> presenter.onDelete());
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
        deleteButton.setVisible(this.isDeleteButtonVisible());

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
