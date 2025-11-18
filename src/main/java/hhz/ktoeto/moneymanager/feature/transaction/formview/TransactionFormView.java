package hhz.ktoeto.moneymanager.feature.transaction.formview;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.category.data.SimpleCategoriesProvider;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.formview.validator.TransactionAmountValidator;
import hhz.ktoeto.moneymanager.feature.transaction.formview.validator.TransactionCategoryValidator;
import hhz.ktoeto.moneymanager.feature.transaction.formview.validator.TransactionDescriptionValidator;
import hhz.ktoeto.moneymanager.ui.AbstractFormView;
import hhz.ktoeto.moneymanager.ui.component.field.AmountInputCalculator;
import hhz.ktoeto.moneymanager.ui.component.field.CategorySelectField;
import hhz.ktoeto.moneymanager.ui.component.field.IncomeExpenseToggle;
import hhz.ktoeto.moneymanager.ui.component.field.RussianDatePicker;
import hhz.ktoeto.moneymanager.ui.mixin.CanAddCategory;

import java.time.LocalDate;

public abstract class TransactionFormView extends AbstractFormView<Transaction> {

    private final CanAddCategory categoryAddDelegate;
    private final SimpleCategoriesProvider categoryProvider;

    private final IncomeExpenseToggle<Transaction.Type> typeToggle;
    private final CategorySelectField categorySelect;
    private final AmountInputCalculator amountInput;
    private final DatePicker datePicker;
    private final TextArea descriptionArea;

    protected TransactionFormView(TransactionFormPresenter presenter, SimpleCategoriesProvider categoryProvider) {
        super(presenter, Transaction.class);
        this.categoryAddDelegate = presenter;
        this.categoryProvider = categoryProvider;

        this.typeToggle = new IncomeExpenseToggle<>(Transaction.Type.EXPENSE, Transaction.Type.INCOME);
        this.categorySelect = new CategorySelectField();
        this.amountInput = new AmountInputCalculator();
        this.datePicker = new RussianDatePicker("Дата", LocalDate.now());
        this.descriptionArea = new TextArea("Описание");
    }

    @Override
    protected void configureRootContent(FlexLayout root) {
        FlexLayout firstRow = new FlexLayout();
        FlexLayout secondRow = new FlexLayout();

        this.configureFirstRow(firstRow);
        this.configureSecondRow(secondRow);
        this.descriptionArea.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignSelf.CENTER
        );

        root.add(
                firstRow,
                secondRow,
                this.descriptionArea
        );
    }

    @Override
    protected void configureBinder(Binder<Transaction> binder) {
        binder.forField(this.typeToggle)
                .asRequired("Не выбран тип")
                .bind(Transaction::getType, Transaction::setType);
        binder.forField(this.categorySelect)
                .withValidator(new TransactionCategoryValidator())
                .bind(Transaction::getCategory, Transaction::setCategory);
        binder.forField(this.amountInput)
                .withValidator(new TransactionAmountValidator())
                .bind(Transaction::getAmount, Transaction::setAmount);
        binder.forField(this.datePicker)
                .asRequired("Не выбрана дата")
                .bind(Transaction::getDate, Transaction::setDate);
        binder.forField(this.descriptionArea)
                .withValidator(new TransactionDescriptionValidator())
                .bind(Transaction::getDescription, Transaction::setDescription);
    }

    private void configureFirstRow(FlexLayout row) {
        this.categorySelect.setItems(this.categoryProvider);
        this.categorySelect.addButtonClickListener(event -> this.categoryAddDelegate.onCategoryAdd());

        row.add(this.typeToggle, this.categorySelect);
        row.addClassNames(
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.FlexDirection.Breakpoint.Small.ROW,
                LumoUtility.AlignItems.BASELINE,
                LumoUtility.Gap.XSMALL
        );
    }

    private void configureSecondRow(FlexLayout row) {
        this.amountInput.setWidthFull();
        this.datePicker.setWidthFull();

        row.add(this.amountInput, this.datePicker);
        row.addClassNames(
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.FlexDirection.Breakpoint.Small.ROW,
                LumoUtility.AlignItems.STRETCH,
                LumoUtility.Gap.XSMALL
        );
    }
}
