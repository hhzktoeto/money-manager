package hhz.ktoeto.moneymanager.ui.feature.transaction.ui.form;

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
import hhz.ktoeto.moneymanager.ui.component.AmountInputCalculator;
import hhz.ktoeto.moneymanager.ui.component.IncomeExpenseToggle;
import hhz.ktoeto.moneymanager.ui.component.RussianDatePicker;
import hhz.ktoeto.moneymanager.ui.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.ui.feature.category.ui.data.CategoryDataProvider;
import hhz.ktoeto.moneymanager.ui.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.ui.feature.transaction.ui.form.validator.TransactionAmountValidator;
import hhz.ktoeto.moneymanager.ui.feature.transaction.ui.form.validator.TransactionDescriptionValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.vaadin.addons.gl0b3.materialicons.MaterialIcons;

import java.time.LocalDate;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
public class TransactionForm extends Composite<FlexLayout> {

    private final CategoryDataProvider categoryProvider;
    private final transient Consumer<TransactionForm> categoryAddAction;
    private final transient Consumer<TransactionForm> submitAction;
    private final transient Consumer<TransactionForm> cancelAction;
    private final transient Consumer<TransactionForm> deleteAction;

    private final Binder<Transaction> binder = new Binder<>(Transaction.class);

    private IncomeExpenseToggle<Transaction.Type> typeToggle;
    private ComboBox<Category> categorySelect;
    private AmountInputCalculator amountInput;
    private DatePicker datePicker;
    private TextArea descriptionArea;
    private Button createCategoryButton;
    private Button submitButton;
    private Button cancelButton;
    private Button deleteButton;

    @Override
    protected FlexLayout initContent() {
        FlexLayout root = new FlexLayout();
        root.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        root.addClassNames(
                LumoUtility.Padding.NONE,
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.STRETCH
        );

        categorySelect = new ComboBox<>("Категория");
        categorySelect.setItems(categoryProvider);
        categorySelect.setItemLabelGenerator(Category::getName);
        categorySelect.setWidthFull();

        createCategoryButton = new Button(VaadinIcon.PLUS.create());
        createCategoryButton.addClickListener(e -> categoryAddAction.accept(this));
        createCategoryButton.setTooltipText("Добавить категорию");

        HorizontalLayout categoryWrapper = new HorizontalLayout(categorySelect, createCategoryButton);
        categoryWrapper.setPadding(false);
        categoryWrapper.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.BASELINE,
                LumoUtility.JustifyContent.BETWEEN,
                LumoUtility.Gap.XSMALL
        );

        typeToggle = new IncomeExpenseToggle<>(Transaction.Type.EXPENSE, Transaction.Type.INCOME);
        FlexLayout firstRow = new FlexLayout(typeToggle, categoryWrapper);
        firstRow.addClassNames(
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.FlexDirection.Breakpoint.Small.ROW,
                LumoUtility.AlignItems.BASELINE,
                LumoUtility.Gap.XSMALL
        );
        root.add(firstRow);

        amountInput = new AmountInputCalculator();
        amountInput.setWidthFull();

        datePicker = new RussianDatePicker("Дата", LocalDate.now());
        datePicker.setWidthFull();

        FlexLayout secondRow = new FlexLayout(amountInput, datePicker);
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
        submitButton.addClickListener(e -> submitAction.accept(this));
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        cancelButton = new Button("Отмена");
        cancelButton.addClickListener(e -> cancelAction.accept(this));

        deleteButton = new Button(MaterialIcons.DELETE.create());
        deleteButton.addClickListener(e -> deleteAction.accept(this));
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);

        HorizontalLayout submitCancelButtons = new HorizontalLayout(cancelButton, submitButton);
        submitCancelButtons.addClassNames(
                LumoUtility.JustifyContent.END,
                LumoUtility.Gap.MEDIUM
        );
        HorizontalLayout buttons = new HorizontalLayout(deleteButton, submitCancelButtons);
        buttons.addClassNames(
                LumoUtility.AlignItems.STRETCH,
                LumoUtility.Margin.Top.MEDIUM,
                LumoUtility.JustifyContent.BETWEEN
        );
        root.add(buttons);

        this.binder.forField(typeToggle)
                .asRequired("Не выбран тип")
                .bind(Transaction::getType, Transaction::setType);
        this.binder.forField(categorySelect)
                .asRequired("Не выбрана категория")
                .bind(Transaction::getCategory, Transaction::setCategory);
        this.binder.forField(amountInput)
                .asRequired("Не введена сумма")
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

    public void edit(Transaction transaction) {
        binder.setBean(transaction);
    }

    Transaction getEditedTransaction() {
        return binder.getBean();
    }

    boolean writeToIfValid(Transaction transaction) {
        try {
            binder.writeBean(transaction);
            return true;
        } catch (ValidationException e) {
            log.error("Failed to validate the transaction");
            log.error(e.getValidationErrors().toString());
            return false;
        }
    }

    void reset(LocalDate date, Category category, Transaction.Type type) {
        Transaction reset = new Transaction();
        reset.setType(type);
        reset.setDate(date);
        reset.setCategory(category);

        binder.setBean(reset);
    }
}
