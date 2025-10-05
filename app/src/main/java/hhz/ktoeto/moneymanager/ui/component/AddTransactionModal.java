package hhz.ktoeto.moneymanager.ui.component;

import com.udojava.evalex.Expression;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.event.TransactionAddedEvent;
import hhz.ktoeto.moneymanager.transaction.model.category.Category;
import hhz.ktoeto.moneymanager.transaction.model.transaction.TransactionDTO;
import hhz.ktoeto.moneymanager.transaction.service.CategoryService;
import hhz.ktoeto.moneymanager.transaction.service.TransactionService;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import java.time.LocalDate;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class AddTransactionModal extends Composite<Dialog> {

    private final transient CategoryService categoryService;
    private final transient TransactionService transactionService;
    private final transient ApplicationEventPublisher eventPublisher;

    private final TransactionTypeToggleSwitch typeToggleSwitch = new TransactionTypeToggleSwitch();
    private final ComboBox<Category> categorySelect = new ComboBox<>("Категория");
    private final TextField amountField = new TextField("Сумма");
    private final DatePicker datePicker = new DatePicker("Дата", LocalDate.now());
    private final TextArea descriptionArea = new TextArea("Описание");
    private final Button addButton = new Button("Добавить");
    private final Button cancelButton = new Button("Отмена");
    private final Button closeButton = new Button(VaadinIcon.CLOSE.create());

    private final Binder<TransactionDTO> transactionBinder = new Binder<>(TransactionDTO.class);

    private ListDataProvider<Category> categoryDataProvider;

    public void open() {
        if (this.getContent().isOpened()) {
            this.getContent().close();
        }
        this.getContent().open();
    }

    @Override
    protected Dialog initContent() {
        Dialog root = new Dialog();
        long userId = SecurityUtils.getCurrentUser().getId();
        categoryDataProvider = new ListDataProvider<>(categoryService.getAll(userId));

        HorizontalLayout header = new HorizontalLayout(new H3("Добавить транзакцию"), closeButton);
        HorizontalLayout firstRow = new HorizontalLayout(typeToggleSwitch, categorySelect);
        HorizontalLayout secondRow = new HorizontalLayout(amountField, datePicker);
        HorizontalLayout buttonsLayout = new HorizontalLayout(cancelButton, addButton);

        VerticalLayout content = new VerticalLayout(firstRow, secondRow, buttonsLayout);
        root.add(header, content);

        transactionBinder.forField(categorySelect)
                .asRequired("Не выбрана категория")
                .bind("category");

        categorySelect.addClassName("category");
        amountField.addClassName("amount");
        header.addClassName("header");
        firstRow.addClassName("first-row");
        secondRow.addClassName("second-row");
        buttonsLayout.addClassName("buttons");

        cancelButton.addClickListener(e -> root.close());
        closeButton.addClickListener(e -> root.close());
        addButton.addClickListener(e -> {
            TransactionDTO transaction = new TransactionDTO(
                    typeToggleSwitch.getSelectedType(),
                    categorySelect.getValue().getName(),
                    datePicker.getValue(),
                    new Expression(amountField.getValue()).eval(),
                    descriptionArea.getOptionalValue().orElse(null)
            );
            transactionService.create(transaction, userId);

            eventPublisher.publishEvent(new TransactionAddedEvent(this));

            amountField.clear();
            descriptionArea.clear();
        });

        content.addClassName("content");

        root.setModal(true);
        root.setCloseOnOutsideClick(false);
        root.setClassName("add-transaction-modal");

        return root;
    }

    @EventListener(TransactionAddedEvent.class)
    private void onTransactionAdded() {
        categoryDataProvider.refreshAll();
    }
}


