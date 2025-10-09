package hhz.ktoeto.moneymanager.ui.transaction.form;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.LayoutProvider;

@UIScope
@SpringComponent("transactionCreate")
public class TransactionCreateFormLayoutProvider implements LayoutProvider<TransactionForm> {

    @Override
    public Component createLayout(TransactionForm form) {
        VerticalLayout layout = new VerticalLayout();
        TransactionForm.Components components = form.components();

        HorizontalLayout firstRow = new HorizontalLayout();
        firstRow.addClassName(LumoUtility.AlignItems.BASELINE);
        firstRow.add(components.typeToggleSwitch(), components.categorySelect(), components.addCategoryButton());
        layout.add(firstRow);

        HorizontalLayout secondRow = new HorizontalLayout();
        secondRow.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.START,
                LumoUtility.JustifyContent.BETWEEN
        );
        secondRow.add(components.amountField(), components.datePicker());

        HorizontalLayout buttonsLayout = new HorizontalLayout(
                components.cancelButton(),
                components.submitButton()
        );

        layout.add(firstRow);
        layout.add(secondRow);
        layout.add(components.descriptionArea());
        layout.add(buttonsLayout);

        return layout;
    }
}
