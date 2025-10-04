package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.transaction.model.category.Category;
import hhz.ktoeto.moneymanager.transaction.service.CategoryService;
import hhz.ktoeto.moneymanager.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class AddTransactionModal extends Composite<Dialog> {

    private final transient CategoryService categoryService;
    private final transient TransactionService transactionService;

    private final TransactionTypeToggleSwitch typeToggleSwitch = new TransactionTypeToggleSwitch();
    private final ComboBox<Category> categorySelect = new ComboBox<>("Категория");
    private final TextField amountField = new TextField("Сумма");
    private final DatePicker datePicker = new DatePicker("Дата", LocalDate.now());
    private final TextArea descriptionField = new TextArea("Описание");
    private final Button addButton = new Button("Добавить");
    private final Button cancelButton = new Button("Отмена");
    private final Button closeButton = new Button(VaadinIcon.CLOSE.create());

    public void open() {
        this.getContent().open();
    }

    @Override
    protected Dialog initContent() {
        Dialog root = new Dialog();
        root.setModal(true);
        root.setCloseOnOutsideClick(false);
        root.setClassName("add-transaction-modal");

        HorizontalLayout header = new HorizontalLayout(new H3("Добавить транзакцию"), closeButton);
        header.addClassName("add-transaction-modal-header");

        HorizontalLayout content = new HorizontalLayout(typeToggleSwitch, categorySelect);
        content.addClassName("add-transaction-modal-content");
        categorySelect.addClassName("add-transaction-modal-category");

        cancelButton.addClickListener(e -> root.close());
        closeButton.addClickListener(e -> root.close());

        root.add(header, content);

        return root;
    }
}


