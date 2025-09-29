package hhz.ktoeto.moneymanager.ui.form;

import com.udojava.evalex.Expression;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import hhz.ktoeto.moneymanager.transaction.model.category.Category;
import hhz.ktoeto.moneymanager.transaction.model.transaction.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class AddTransactionForm extends VerticalLayout {

    private final Select<Transaction.Type> typeSelect = new Select<>();
    private final ComboBox<Category> categorySelect = new ComboBox<>("Категория");
    private final TextField amountField = new TextField("Сумма");
    private final DatePicker datePicker = new DatePicker("Дата", LocalDate.now());
    private final TextArea descriptionField = new TextArea("Описание");
    private final Button addButton = new Button("Добавить");

    private final HorizontalLayout mainFieldsLayout = new HorizontalLayout(typeSelect, categorySelect, amountField, datePicker);
    private final HorizontalLayout descriptionFieldLayout = new HorizontalLayout(descriptionField);
    private final HorizontalLayout addButtonLayout = new HorizontalLayout(addButton);

    public AddTransactionForm() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        this.typeSelect.setLabel("Тип");
        this.typeSelect.setItems(Transaction.Type.values());
        this.typeSelect.setItemLabelGenerator(type -> Transaction.Type.EXPENSE == type ?
                "Расход" : "Доход");
        this.mainFieldsLayout.setAlignItems(Alignment.STRETCH);
        this.mainFieldsLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        this.descriptionFieldLayout.setAlignItems(Alignment.STRETCH);
        this.descriptionFieldLayout.setSizeFull();
        this.addButtonLayout.setAlignItems(Alignment.STRETCH);
        this.addButtonLayout.setJustifyContentMode(JustifyContentMode.END);
        this.categorySelect.setItemLabelGenerator(Category::getName);

        setAlignItems(Alignment.STRETCH);
        add(this.mainFieldsLayout, this.descriptionFieldLayout, this.addButtonLayout);
    }

    public void addSubmitListener(Runnable listener) {
        this.addButton.addClickListener(ignored -> listener.run());
    }
    public void addCategories(List<Category> categories) {
        categories.sort(Comparator.comparing(Category::getName));
        this.categorySelect.setItems(categories);
    }

    public Transaction.Type type() {
        return typeSelect.getValue();
    }

    public Category category() {
        return categorySelect.getValue();
    }

    public BigDecimal amount() {
        Expression expression = new Expression(amountField.getValue());
        return expression.eval();
    }

    public LocalDate date() {
        return datePicker.getValue();
    }

    public String description() {
        return descriptionField.getValue();
    }
}
