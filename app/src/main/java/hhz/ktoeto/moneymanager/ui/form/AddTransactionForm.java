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
import com.vaadin.flow.theme.lumo.LumoUtility;
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

    private final HorizontalLayout firstRow = new HorizontalLayout(typeSelect, categorySelect, amountField, datePicker);
    private final HorizontalLayout secondRow = new HorizontalLayout(descriptionField, addButton);

    public AddTransactionForm() {
        this.typeSelect.setLabel("Тип");
        this.typeSelect.setItems(Transaction.Type.values());
        this.typeSelect.setItemLabelGenerator(type -> Transaction.Type.EXPENSE == type ?
                "Расход" : "Доход");
        this.categorySelect.setItemLabelGenerator(Category::getName);

        this.firstRow.addClassNames(
                LumoUtility.JustifyContent.BETWEEN
        );
        this.secondRow.addClassNames(
                LumoUtility.JustifyContent.BETWEEN
        );

        add(this.firstRow, this.secondRow);
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
