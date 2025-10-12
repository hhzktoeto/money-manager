package hhz.ktoeto.moneymanager.ui.transaction.form;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.backend.entity.Category;
import hhz.ktoeto.moneymanager.backend.entity.Transaction;
import hhz.ktoeto.moneymanager.ui.category.CategoryDataProvider;
import hhz.ktoeto.moneymanager.ui.component.RussianDatePicker;
import hhz.ktoeto.moneymanager.ui.component.TransactionTypeToggleSwitch;
import hhz.ktoeto.moneymanager.ui.transaction.converter.MathExpressionToBigDecimalConverter;
import hhz.ktoeto.moneymanager.ui.transaction.validator.TransactionAmountValidator;
import hhz.ktoeto.moneymanager.ui.transaction.validator.TransactionDescriptionValidator;

import java.time.LocalDate;

public final class TransactionForm extends Composite<FlexLayout> {

    private final transient TransactionFormLogic formLogic;
    private final CategoryDataProvider categoryProvider;

    private TransactionTypeToggleSwitch typeToggleSwitch;
    private ComboBox<Category> categorySelect;
    private TextField amountField;
    private DatePicker datePicker;
    private TextArea descriptionArea;
    private Button createCategoryButton;
    private Button submitButton;
    private Button cancelButton;

    private Binder<Transaction> binder;
    private transient Transaction editableTransaction;

    TransactionForm(CategoryDataProvider categoryProvider, TransactionFormLogic formLogic) {
        this.formLogic = formLogic;
        this.categoryProvider = categoryProvider;
    }

    public void edit(Transaction transaction) {
        editableTransaction = transaction;
        binder.readBean(editableTransaction);
    }

    Transaction.Type selectedType() {
        return typeToggleSwitch.getSelectedType();
    }

    boolean writeTo(Transaction transaction) {
        return binder.writeBeanIfValid(transaction);
    }

    Transaction getEditableTransaction() {
        return editableTransaction;
    }

    @Override
    protected FlexLayout initContent() {
        FlexLayout root = new FlexLayout();
        root.setFlexDirection(FlexLayout.FlexDirection.COLUMN);

        typeToggleSwitch = new TransactionTypeToggleSwitch();
        typeToggleSwitch.setWidthFull();

        categorySelect = new ComboBox<>("Категория");
        categorySelect.setItems(categoryProvider);
        categorySelect.setItemLabelGenerator(Category::getName);
        categorySelect.setWidthFull();

        createCategoryButton = new Button(VaadinIcon.PLUS.create());
        createCategoryButton.addClickListener(e -> formLogic.onCategoryAdd(this));
        createCategoryButton.setTooltipText("Добавить категорию");

        HorizontalLayout categoryWrapper = new HorizontalLayout(categorySelect, createCategoryButton);
        categoryWrapper.setPadding(false);
        categoryWrapper.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.BASELINE,
                LumoUtility.JustifyContent.BETWEEN,
                LumoUtility.Gap.XSMALL
        );

        FlexLayout firstRow = new FlexLayout(typeToggleSwitch, categoryWrapper);
        firstRow.addClassNames(
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.FlexDirection.Breakpoint.Small.ROW,
                LumoUtility.AlignItems.BASELINE,
                LumoUtility.Gap.XSMALL
        );
        root.add(firstRow);

        amountField = new TextField("Сумма");
        amountField.setWidthFull();

        datePicker = new RussianDatePicker("Дата", LocalDate.now());
        datePicker.setWidthFull();

        FlexLayout secondRow = new FlexLayout(amountField, datePicker);
        secondRow.addClassNames(
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.FlexDirection.Breakpoint.Small.ROW,
                LumoUtility.AlignItems.STRETCH,
                LumoUtility.Gap.XSMALL
        );
        root.add(secondRow);

        descriptionArea = new TextArea("Описание");
        descriptionArea.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignSelf.CENTER
        );
        root.add(descriptionArea);

        submitButton = new Button("Сохранить");
        submitButton.addClickListener(e -> formLogic.onSubmit(this));
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        cancelButton = new Button("Отмена");
        cancelButton.addClickListener(e -> formLogic.onCancel(this));

        HorizontalLayout buttons = new HorizontalLayout(cancelButton, submitButton);
        buttons.addClassNames(
                LumoUtility.Margin.Top.MEDIUM,
                LumoUtility.JustifyContent.END,
                LumoUtility.Gap.LARGE
        );
        root.add(buttons);

        this.binder = new Binder<>(Transaction.class);
        this.binder.forField(categorySelect)
                .asRequired("Не выбрана категория")
                .bind(Transaction::getCategory, Transaction::setCategory);

        this.binder.forField(amountField)
                .asRequired("Не введена сумма")
                .withConverter(new MathExpressionToBigDecimalConverter())
                .withValidator(new TransactionAmountValidator())
                .bind(Transaction::getAmount, Transaction::setAmount);

        this.binder.forField(datePicker)
                .asRequired("Не выбрана дата")
                .bind(Transaction::getDate, Transaction::setDate);

        this.binder.forField(descriptionArea)
                .withValidator(new TransactionDescriptionValidator())
                .bind(Transaction::getDescription, Transaction::setDescription);


        return root;
    }

    Components components() {
        return new Components(
                typeToggleSwitch, categorySelect, amountField, datePicker, descriptionArea, createCategoryButton,
                submitButton, cancelButton
        );
    }

    record Components(
            TransactionTypeToggleSwitch typeToggleSwitch,
            ComboBox<Category> categorySelect,
            TextField amountField,
            DatePicker datePicker,
            TextArea descriptionArea,
            Button addCategoryButton,
            Button submitButton,
            Button cancelButton
    ) {
    }
}
