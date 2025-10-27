package hhz.ktoeto.moneymanager.feature.transaction.renderer;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class TransactionCategoryDateRenderer extends ComponentRenderer<HorizontalLayout, Transaction> {

    //TODO: Добавить иконки для категория, когда они появятся
    public TransactionCategoryDateRenderer() {
        super(transaction -> {
            HorizontalLayout layout = new HorizontalLayout();
            layout.addClassNames(LumoUtility.Padding.XSMALL);
            Category transactionCategory = transaction.getCategory();
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .appendPattern("dd MMMM yyyy")
                    .toFormatter()
                    .withLocale(Locale.of("ru"));

            Span categoryName = new Span(transactionCategory.getName());
            categoryName.addClassNames(
                    LumoUtility.FontWeight.BOLD,
                    LumoUtility.FontSize.MEDIUM
            );
            Span transactionDate = new Span(formatter.format(transaction.getDate()));
            transactionDate.addClassNames(
                    LumoUtility.TextColor.SECONDARY,
                    LumoUtility.FontWeight.LIGHT,
                    LumoUtility.FontSize.SMALL
            );

            VerticalLayout nameDateLayout = new VerticalLayout(categoryName, transactionDate);
            nameDateLayout.setPadding(false);
            nameDateLayout.addClassName(LumoUtility.Gap.XSMALL);

            layout.add(nameDateLayout);
            return layout;
        });
    }
}
