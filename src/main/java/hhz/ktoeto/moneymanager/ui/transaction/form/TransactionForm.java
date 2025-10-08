package hhz.ktoeto.moneymanager.ui.transaction.form;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import hhz.ktoeto.moneymanager.backend.entity.Category;
import hhz.ktoeto.moneymanager.backend.entity.Transaction;
import hhz.ktoeto.moneymanager.ui.category.CategoryDataProvider;
import hhz.ktoeto.moneymanager.ui.component.RussianDatePicker;
import hhz.ktoeto.moneymanager.ui.component.TransactionTypeToggleSwitch;
import hhz.ktoeto.moneymanager.ui.transaction.converter.MathExpressionToBigDecimalConverter;
import hhz.ktoeto.moneymanager.ui.transaction.validator.TransactionAmountValidator;
import hhz.ktoeto.moneymanager.ui.transaction.validator.TransactionDescriptionValidator;

import java.time.LocalDate;

public final class TransactionForm {

    private final TransactionTypeToggleSwitch typeToggleSwitch = new TransactionTypeToggleSwitch();
    private final ComboBox<Category> categorySelect = new ComboBox<>("Категория");
    private final TextField amountField = new TextField("Сумма");
    private final DatePicker datePicker = new RussianDatePicker("Дата", LocalDate.now());
    private final TextArea descriptionArea = new TextArea("Описание");
    private final Button addCategoryButton = new Button(VaadinIcon.PLUS.create());
    private final Button submitButton = new Button("Сохранить");
    private final Button cancelButton = new Button("Отмена");

    private final Binder<Transaction> binder = new Binder<>(Transaction.class);

    TransactionForm(CategoryDataProvider categoryProvider, TransactionFormLogic logic) {
        addCategoryButton.addClickListener(e -> logic.onCategoryAdd(this));
        submitButton.addClickListener(e -> logic.onSubmit(this));
        cancelButton.addClickListener(e -> logic.onCancel(this));

        categorySelect.setItems(categoryProvider);
        categorySelect.setItemLabelGenerator(Category::getName);

        binder.forField(categorySelect)
                .asRequired("Не выбрана категория")
                .bind(Transaction::getCategory, Transaction::setCategory);

        binder.forField(amountField)
                .asRequired("Не введена сумма")
                .withConverter(new MathExpressionToBigDecimalConverter())
                .withValidator(new TransactionAmountValidator())
                .bind(Transaction::getAmount, Transaction::setAmount);

        binder.forField(datePicker)
                .asRequired("Не выбрана дата")
                .bind(Transaction::getDate, Transaction::setDate);

        binder.forField(descriptionArea)
                .withValidator(new TransactionDescriptionValidator())
                .bind(Transaction::getDescription, Transaction::setDescription);
    }

    public Transaction.Type selectedType() {
        return typeToggleSwitch.getSelectedType();
    }

    public boolean writeTo(Transaction transaction) {
        return binder.writeBeanIfValid(transaction);
    }

    Components components() {
        return new Components(typeToggleSwitch, categorySelect, amountField, datePicker, descriptionArea, submitButton, cancelButton);
    }

    record Components(
            TransactionTypeToggleSwitch typeToggleSwitch,
            ComboBox<Category> categorySelect,
            TextField amountField,
            DatePicker datePicker,
            TextArea descriptionArea,
            Button submitButton,
            Button cancelButton
    ) {
    }
}
