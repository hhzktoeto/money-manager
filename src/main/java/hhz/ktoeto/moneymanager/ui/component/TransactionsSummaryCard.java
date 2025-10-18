package hhz.ktoeto.moneymanager.ui.component;

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
    private BigDecimal amount;

    private Span amountSpan;

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
        this.amountSpan.setText(formattingService.formatAmount(amount));

        this.reloadColors();
    }

    @Override
    protected BasicContainer initContent() {
        BasicContainer root = new BasicContainer();

        HorizontalLayout header = new HorizontalLayout();
        header.setWidthFull();
        header.addClassNames(
                LumoUtility.JustifyContent.BETWEEN
        );
        header.add(title);

        String colorClassName = switch (mode) {
            case EXPENSE -> LumoUtility.TextColor.ERROR;
            case INCOME -> LumoUtility.TextColor.SUCCESS;
            default -> LumoUtility.TextColor.BODY;
        };
        Span iconSpan = new Span(icon.create());
        iconSpan.addClassName(colorClassName);
        header.add(iconSpan);

        root.setHeader(header);

        amountSpan = new Span(formattingService.formatAmount(amount));
        amountSpan.addClassNames(
                LumoUtility.FontWeight.BOLD,
                LumoUtility.FontSize.XXXLARGE,
                LumoUtility.FontSize.Breakpoint.Small.XLARGE,
                LumoUtility.FontSize.Breakpoint.Large.XXXLARGE,
                LumoUtility.Padding.Top.SMALL,
                LumoUtility.Padding.Left.SMALL
        );
        this.reloadColors();

        root.setContent(amountSpan);

        return root;
    }

    private void reloadColors() {
        String className = switch (mode) {
            case EXPENSE -> amount.compareTo(BigDecimal.ZERO) > 0
                    ? LumoUtility.TextColor.ERROR
                    : LumoUtility.TextColor.BODY;
            case INCOME -> amount.compareTo(BigDecimal.ZERO) > 0
                    ? LumoUtility.TextColor.SUCCESS
                    : LumoUtility.TextColor.BODY;
            default -> LumoUtility.TextColor.BODY;
        };
        amountSpan.addClassName(className);

    }

    public enum Mode {
        EXPENSE,
        INCOME,
        BALANCE
    }
}
