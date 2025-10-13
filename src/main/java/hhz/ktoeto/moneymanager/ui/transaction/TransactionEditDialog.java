package hhz.ktoeto.moneymanager.ui.transaction;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.transaction.event.*;
import hhz.ktoeto.moneymanager.ui.transaction.form.TransactionForm;
import hhz.ktoeto.moneymanager.ui.transaction.form.TransactionFormFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class TransactionEditDialog extends Composite<Dialog> {

    private final transient TransactionFormFactory formFactory;

    private Button closeButton;
    private HorizontalLayout header;
    private TransactionForm form;

    @Override
    protected Dialog initContent() {
        Dialog root = new Dialog();
        root.setCloseOnOutsideClick(false);
        root.setWidthFull();
        root.addClassNames(
                LumoUtility.MaxWidth.SCREEN_SMALL
        );

        header = new HorizontalLayout();
        header.add(new H3("Редактировать транзакцию"));
        header.addClassNames(
                LumoUtility.Margin.Bottom.MEDIUM,
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.BETWEEN
        );

        closeButton = new Button(VaadinIcon.CLOSE.create());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE, ButtonVariant.LUMO_ERROR);
        closeButton.addClickListener(e -> this.close());
        header.add(closeButton);
        root.add(header);

        form = formFactory.transactionEditForm();
        form.addClassNames(
                LumoUtility.Padding.NONE,
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.STRETCH
        );

        root.add(form);

        return root;
    }

    @EventListener(OpenTransactionEditDialogEvent.class)
    private void open(OpenTransactionEditDialogEvent event) {
        if (this.getContent().isOpened()) {
            this.getContent().close();
        }
        this.getContent().open();
        form.edit(event.getTransaction());
    }

    @EventListener({
            TransactionEditCancelledEvent.class,
            TransactionUpdatedEvent.class
    })
    private void close() {
        this.getContent().close();
    }
}
