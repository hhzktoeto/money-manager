package hhz.ktoeto.moneymanager.ui.component.modal;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.event.TransactionAddedEvent;
import hhz.ktoeto.moneymanager.transaction.entity.Category;
import hhz.ktoeto.moneymanager.transaction.entity.Transaction;
import hhz.ktoeto.moneymanager.transaction.service.CategoryService;
import hhz.ktoeto.moneymanager.transaction.service.TransactionService;
import hhz.ktoeto.moneymanager.ui.component.common.RussianDatePicker;
import hhz.ktoeto.moneymanager.ui.component.common.TransactionTypeToggleSwitch;
import hhz.ktoeto.moneymanager.ui.converter.CategoryNameToCategoryConverter;
import hhz.ktoeto.moneymanager.ui.converter.MathExpressionToBigDecimalConverter;
import hhz.ktoeto.moneymanager.ui.validator.TransactionAmountValidator;
import hhz.ktoeto.moneymanager.ui.validator.TransactionDescriptionValidator;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import java.time.LocalDate;

@Slf4j
@UIScope
@SpringComponent
@RequiredArgsConstructor
public class AddTransactionModal extends Composite<Dialog> {

    private final transient CategoryService categoryService;
    private final transient TransactionService transactionService;
    private final transient ApplicationEventPublisher eventPublisher;

    private final transient CategoryNameToCategoryConverter categoryConverter;
    private final transient MathExpressionToBigDecimalConverter mathExpressionConverter;

    private final transient TransactionAmountValidator transactionAmountValidator;
    private final transient TransactionDescriptionValidator transactionDescriptionValidator;

    private final TransactionTypeToggleSwitch typeToggleSwitch = new TransactionTypeToggleSwitch();
    private final ComboBox<String> categorySelect = new ComboBox<>("Категория");
    private final TextField amountField = new TextField("Сумма");
    private final DatePicker datePicker = new RussianDatePicker("Дата", LocalDate.now());
    private final TextArea descriptionArea = new TextArea("Описание");
    private final Button addButton = new Button("Добавить");
    private final Button cancelButton = new Button("Отмена");
    private final Button closeButton = new Button(VaadinIcon.CLOSE.create());

    private final HorizontalLayout header = new HorizontalLayout(new H3("Добавить транзакцию"), closeButton);
    private final HorizontalLayout firstRow = new HorizontalLayout(typeToggleSwitch, categorySelect);
    private final HorizontalLayout secondRow = new HorizontalLayout(amountField, datePicker);
    private final HorizontalLayout buttonsLayout = new HorizontalLayout(cancelButton, addButton);
    private final VerticalLayout content = new VerticalLayout(header, firstRow, secondRow, descriptionArea, buttonsLayout);

    private final Binder<Transaction> transactionBinder = new Binder<>(Transaction.class);

    private ListDataProvider<String> categoryDataProvider;

    public void open() {
        if (this.getContent().isOpened()) {
            this.getContent().close();
        }
        this.getContent().open();
    }

    @Override
    protected Dialog initContent() {
        Dialog root = new Dialog(content);
        long userId = SecurityUtils.getCurrentUser().getId();
        categoryDataProvider = new ListDataProvider<>(categoryService.getAll(userId)
                .stream()
                .map(Category::getName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList()
        );

        this.configureBinder();
        cancelButton.addClickListener(e -> this.onClose());
        closeButton.addClickListener(e -> this.onClose());
        addButton.addClickListener(e -> this.onSave(userId));

        categorySelect.setAllowCustomValue(true);
        categorySelect.addCustomValueSetListener(e -> {
            String value = e.getDetail();
            if (value != null && !value.isBlank()) {
                categorySelect.setValue(value);
            }
        });
        categorySelect.setItems(categoryDataProvider);

        root.setCloseOnOutsideClick(false);

        root.addClassName("add-transaction-modal");
        content.addClassName("content");
        header.addClassName("header");
        firstRow.addClassName("first-row");
        secondRow.addClassName("second-row");
        buttonsLayout.addClassName("buttons");
        categorySelect.addClassName("category");
        amountField.addClassName("amount");

        return root;
    }

    private void onClose() {
        this.getContent().close();
    }

    private void onSave(long userId) {
        Transaction transaction = new Transaction();
        transaction.setType(typeToggleSwitch.getSelectedType());
        transaction.setUserId(userId);

        boolean valid = transactionBinder.writeBeanIfValid(transaction);
        if (!valid) {
            Notification.show("Проверьте корректность заполнения", 1500, Notification.Position.TOP_CENTER);
            return;
        }
        transactionService.create(transaction);
        eventPublisher.publishEvent(new TransactionAddedEvent(this));

        amountField.clear();
        descriptionArea.clear();
        amountField.setInvalid(false);
    }

    private void configureBinder() {
        transactionBinder.forField(categorySelect)
                .asRequired("Не выбрана категория")
                .withConverter(categoryConverter)
                .bind(Transaction::getCategory, Transaction::setCategory);

        transactionBinder.forField(amountField)
                .asRequired("Не введена сумма")
                .withConverter(mathExpressionConverter)
                .withValidator(transactionAmountValidator)
                .bind(Transaction::getAmount, Transaction::setAmount);

        transactionBinder.forField(datePicker)
                .asRequired("Не выбрана дата")
                .bind(Transaction::getDate, Transaction::setDate);

        transactionBinder.forField(descriptionArea)
                .withValidator(transactionDescriptionValidator)
                .bind(Transaction::getDescription, Transaction::setDescription);
    }

    @EventListener(TransactionAddedEvent.class)
    private void onTransactionAdded() {
        UI.getCurrent().access(() -> {
            categoryDataProvider = new ListDataProvider<>(categoryService.getAll(SecurityUtils.getCurrentUser().getId())
                    .stream()
                    .map(Category::getName)
                    .sorted(String.CASE_INSENSITIVE_ORDER)
                    .toList());
            categorySelect.setItems(categoryDataProvider);
        });
    }
}


