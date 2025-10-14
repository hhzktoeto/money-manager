package hhz.ktoeto.moneymanager.feature.transaction.ui;

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
import hhz.ktoeto.moneymanager.feature.transaction.event.OpenTransactionCreateDialogEvent;
import hhz.ktoeto.moneymanager.feature.transaction.event.TransactionCreationCancelledEvent;
import hhz.ktoeto.moneymanager.feature.transaction.ui.form.TransactionForm;
import hhz.ktoeto.moneymanager.feature.transaction.ui.form.TransactionFormFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class TransactionCreateDialog extends Composite<Dialog> {

    private final transient TransactionFormFactory formFactory;

    private Button closeButton;
    private HorizontalLayout header;

    @Override
    protected Dialog initContent() {
        Dialog root = new Dialog();
        root.setCloseOnOutsideClick(false);
        root.setWidthFull();
        root.addClassNames(
                LumoUtility.MaxWidth.SCREEN_SMALL
        );

        header = new HorizontalLayout();
        header.add(new H3("Добавить транзакцию"));
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

        TransactionForm form = formFactory.transactionCreateForm();
        form.addClassNames(
                LumoUtility.Padding.NONE,
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.STRETCH
        );

        root.add(form);

        return root;
    }

    @EventListener(OpenTransactionCreateDialogEvent.class)
    private void open() {
        if (this.getContent().isOpened()) {
            this.getContent().close();
        }
        this.getContent().open();
    }

    @EventListener(TransactionCreationCancelledEvent.class)
    private void close() {
        this.getContent().close();
    }
}


