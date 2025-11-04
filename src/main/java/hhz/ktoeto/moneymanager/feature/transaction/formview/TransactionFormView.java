package hhz.ktoeto.moneymanager.feature.transaction.formview;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.category.data.SimpleAllCategoriesProvider;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.formview.validator.TransactionAmountValidator;
import hhz.ktoeto.moneymanager.feature.transaction.formview.validator.TransactionDescriptionValidator;
import hhz.ktoeto.moneymanager.ui.AbstractFormView;
import hhz.ktoeto.moneymanager.ui.component.AmountInputCalculator;
import hhz.ktoeto.moneymanager.ui.component.IncomeExpenseToggle;
import hhz.ktoeto.moneymanager.ui.component.RussianDatePicker;
import hhz.ktoeto.moneymanager.ui.mixin.CanAddCategory;

import java.time.LocalDate;

public abstract class TransactionFormView extends AbstractFormView<Transaction> {

    private final CanAddCategory categoryAddDelegate;
    private final SimpleAllCategoriesProvider categoryProvider;

    private final IncomeExpenseToggle<Transaction.Type> typeToggle;
    private final ComboBox<Category> categorySelect;
    private final AmountInputCalculator amountInput;
    private final DatePicker datePicker;
    private final TextArea descriptionArea;
    private final Button createCategoryButton;

    protected TransactionFormView(TransactionFormPresenter presenter, SimpleAllCategoriesProvider categoryProvider) {
        super(presenter, Transaction.class);
        this.categoryAddDelegate = presenter;
        this.categoryProvider = categoryProvider;

        this.typeToggle = new IncomeExpenseToggle<>(Transaction.Type.EXPENSE, Transaction.Type.INCOME);
        this.categorySelect = new ComboBox<>("Категория");
        this.amountInput = new AmountInputCalculator();
        this.datePicker = new RussianDatePicker("Дата", LocalDate.now());
        this.descriptionArea = new TextArea("Описание");
        this.createCategoryButton = new Button(VaadinIcon.PLUS.create());
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
                .asRequired("Не выбрана категория")
                .bind(Transaction::getCategory, Transaction::setCategory);
        binder.forField(this.amountInput)
                .asRequired()
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
        this.categorySelect.setItemLabelGenerator(Category::getName);
        this.categorySelect.setWidthFull();

        this.createCategoryButton.addClickListener(event -> this.categoryAddDelegate.onCategoryAdd());
        this.createCategoryButton.setHeight(this.categorySelect.getHeight());
        this.createCategoryButton.setTooltipText("Добавить категорию");

        HorizontalLayout categoryWrapper = new HorizontalLayout(this.categorySelect, this.createCategoryButton);
        categoryWrapper.setPadding(false);
        categoryWrapper.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.END,
                LumoUtility.JustifyContent.BETWEEN,
                LumoUtility.Gap.XSMALL
        );

        row.add(this.typeToggle, categoryWrapper);
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
