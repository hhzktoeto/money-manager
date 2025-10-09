package hhz.ktoeto.moneymanager.ui.transaction.form;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.ui.LayoutProvider;

@UIScope
@SpringComponent("transactionCreate")
public class TransactionCreateFormLayoutProvider implements LayoutProvider<TransactionForm> {

    @Override
    public Component createLayout(TransactionForm form) {
        VerticalLayout layout = new VerticalLayout();
        TransactionForm.Components components = form.components();
        HorizontalLayout firstRow = new HorizontalLayout(
                components.typeToggleSwitch(),
                components.categorySelect(),
                components.addCategoryButton()
        );
        HorizontalLayout secondRow = new HorizontalLayout(
                components.amountField(),
                components.datePicker()
        );
        HorizontalLayout buttonsLayout = new HorizontalLayout(
                components.cancelButton(),
                components.submitButton()
        );

        layout.add(firstRow, secondRow, buttonsLayout);

        return layout;
    }
}
