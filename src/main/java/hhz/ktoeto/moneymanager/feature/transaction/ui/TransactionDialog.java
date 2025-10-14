package hhz.ktoeto.moneymanager.feature.transaction.ui;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.transaction.event.*;
import hhz.ktoeto.moneymanager.feature.transaction.ui.form.TransactionForm;
import hhz.ktoeto.moneymanager.feature.transaction.ui.form.TransactionFormFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class TransactionDialog extends Composite<Dialog> {

    private final transient TransactionFormFactory formFactory;

    private H3 title;
    private Button closeButton;
    private HorizontalLayout header;
    private FlexLayout content;

    @Override
    protected Dialog initContent() {
        Dialog root = new Dialog();
        root.setCloseOnOutsideClick(false);
        root.addDialogCloseActionListener(event -> this.close());
        root.setWidthFull();
        root.addClassName(LumoUtility.MaxWidth.SCREEN_SMALL);

        header = new HorizontalLayout();
        header.addClassNames(
                LumoUtility.Margin.Bottom.MEDIUM,
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.BETWEEN
        );

        title = new H3();
        header.add(title);

        closeButton = new Button(VaadinIcon.CLOSE.create());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE, ButtonVariant.LUMO_ERROR);
        closeButton.addClickListener(e -> this.close());
        header.add(closeButton);
        root.add(header);

        content = new FlexLayout();
        root.add(content);

        return root;
    }

    @EventListener(OpenTransactionCreateDialogEvent.class)
    private void openTransactionCreation() {
        this.open();

        title.setText("Создать транзакцию");
        TransactionForm form = formFactory.transactionCreateForm();
        content.add(form);
    }

    @EventListener(OpenTransactionEditDialogEvent.class)
    private void openTransactionEdit(OpenTransactionEditDialogEvent event) {
        this.open();

        title.setText("Редактировать транзакцию");
        TransactionForm form = formFactory.transactionEditForm();
        form.edit(event.getTransaction());
        content.add(form);
    }

    @EventListener({
            TransactionCreationCancelledEvent.class,
            TransactionEditCancelledEvent.class,
            TransactionUpdatedEvent.class
    })
    private void close() {
        this.getContent().close();
        content.removeAll();
    }

    private void open() {
        if (this.getContent().isOpened()) {
            this.getContent().close();
        }
        this.getContent().open();
    }
}
