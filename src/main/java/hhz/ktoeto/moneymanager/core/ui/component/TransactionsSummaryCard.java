package hhz.ktoeto.moneymanager.core.ui.component;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.core.service.FormattingService;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class TransactionsSummaryCard extends Composite<BasicContainer> {

    private final transient FormattingService formattingService;

    private final H3 title;
    private final Mode mode;
    private final VaadinIcon icon;
    private final BigDecimal initialAmount;

    private Span amountSpan;

    public void setAmount(BigDecimal amount) {
        this.amountSpan.setText(formattingService.formatAmount(amount));
    }

    @Override
    protected BasicContainer initContent() {
        BasicContainer root = new BasicContainer();

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.JustifyContent.BETWEEN
        );

        String colorClassName = switch (mode) {
            case EXPENSE -> LumoUtility.TextColor.ERROR;
            case INCOME -> LumoUtility.TextColor.SUCCESS;
            default -> LumoUtility.TextColor.BODY;
        };
        Span iconSpan = new Span(icon.create());
        iconSpan.addClassName(colorClassName);

        header.add(title, iconSpan);
        root.setHeader(header);

        amountSpan = new Span(formattingService.formatAmount(initialAmount));
        amountSpan.addClassNames(
                LumoUtility.FontWeight.BOLD,
                LumoUtility.FontSize.XXXLARGE,
                LumoUtility.FontSize.Breakpoint.Small.XLARGE,
                LumoUtility.FontSize.Breakpoint.Large.XXXLARGE,
                LumoUtility.Padding.Top.SMALL,
                LumoUtility.Padding.Left.SMALL
        );
        if (mode != Mode.BALANCE) {
            amountSpan.addClassName(
                    mode == Mode.EXPENSE
                            ? initialAmount.compareTo(BigDecimal.ZERO) > 0
                                ? LumoUtility.TextColor.ERROR
                                : LumoUtility.TextColor.BODY
                            : initialAmount.compareTo(BigDecimal.ZERO) > 0
                                ? LumoUtility.TextColor.SUCCESS
                                : LumoUtility.TextColor.BODY
            );
        }

        root.setContent(amountSpan);

        return root;
    }

    public enum Mode {EXPENSE, INCOME, BALANCE}
}
