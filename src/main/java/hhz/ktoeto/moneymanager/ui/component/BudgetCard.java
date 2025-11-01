package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.progressbar.ProgressBarVariant;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;
import lombok.Builder;
import org.vaadin.addons.gl0b3.materialicons.MaterialIcons;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Function;

public class BudgetCard extends Composite<BasicContainer> {

    private final String title;
    private final BigDecimal currentAmount;
    private final BigDecimal maxAmount;
    private final BigDecimal remainingAmount;
    private final LocalDate endDate;
    private final String typeName;
    private final boolean isFavourite;
    private final boolean isExpense;
    private final Function<BigDecimal, String> amountFormatter;
    private final Function<LocalDate, String> dateFormatter;

    private final Button favouriteButton;

    public BudgetCard(BudgetCardData budgetCardData) {
        this.title = budgetCardData.title();
        this.currentAmount = budgetCardData.currentAmount();
        this.maxAmount = budgetCardData.maxAmount();
        this.remainingAmount = budgetCardData.remainingAmount();
        this.endDate = budgetCardData.endDate();
        this.typeName = budgetCardData.typeName();
        this.isFavourite = budgetCardData.isFavourite();
        this.isExpense = budgetCardData.isExpense();
        this.amountFormatter = budgetCardData.amountFormatter();
        this.dateFormatter = budgetCardData.dateFormatter();

        this.favouriteButton = new Button(MaterialIcons.STAR_BORDER.create());
    }

    @Override
    protected BasicContainer initContent() {
        BasicContainer root = new BasicContainer();

        FlexLayout header = root.getHeader();
        FlexLayout content = root.getContent();

        this.configureHeader(header);
        this.configureContent(content);

        return root;
    }

    public void addContentClickListener(ComponentEventListener<ClickEvent<FlexLayout>> listener) {
        this.getContent().getContent().addClickListener(listener);
    }

    public void addFavouriteButtonClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        this.favouriteButton.addClickListener(listener);
    }

    public void hideAddToFavouriteButton() {
        this.favouriteButton.setVisible(false);
    }

    public void setMinWidth(float width, Unit unit) {
        this.getContent().setMinWidth(width, unit);
    }

    public void setMaxWidth(float width, Unit unit) {
        this.getContent().setMaxWidth(width, unit);
    }

    private void configureHeader(FlexLayout header) {
        H3 title = new H3(this.title);

        Span type = new Span(this.typeName);
        type.getElement().getThemeList().add(this.isExpense
                ? StyleConstants.Badge.ERROR
                : StyleConstants.Badge.SUCCESS
        );

        HorizontalLayout titleTypeLayout = new HorizontalLayout(title, type);
        titleTypeLayout.setSpacing(false);
        titleTypeLayout.setPadding(false);
        titleTypeLayout.addClassNames(
                LumoUtility.Gap.MEDIUM,
                LumoUtility.AlignItems.CENTER
        );

        this.favouriteButton.addThemeVariants(ButtonVariant.LUMO_LARGE, ButtonVariant.LUMO_TERTIARY_INLINE);
        if (this.isFavourite) {
            this.favouriteButton.setIcon(MaterialIcons.STAR.create());
            this.favouriteButton.addThemeVariants(ButtonVariant.LUMO_WARNING);
        }

        header.add(titleTypeLayout, this.favouriteButton);
        header.addClassNames(
                LumoUtility.FlexDirection.ROW,
                LumoUtility.JustifyContent.BETWEEN,
                LumoUtility.Gap.SMALL
        );
    }

    private void configureContent(FlexLayout content) {
        BigDecimal safeAmount = this.currentAmount.min(this.maxAmount);
        BigDecimal leftOver = this.currentAmount.subtract(safeAmount);

        Span current = new Span(this.amountFormatter.apply(this.currentAmount));
        current.addClassNames(
                LumoUtility.FontSize.LARGE,
                LumoUtility.FontWeight.BOLD
        );

        Span goal = new Span("из " + this.amountFormatter.apply(this.maxAmount));
        goal.addClassNames(
                LumoUtility.FontSize.SMALL,
                LumoUtility.FontWeight.LIGHT,
                LumoUtility.TextColor.SECONDARY
        );

        HorizontalLayout amountsLayout = new HorizontalLayout(current, goal);
        amountsLayout.setSpacing(false);
        amountsLayout.setPadding(false);
        amountsLayout.addClassNames(
                LumoUtility.Gap.SMALL,
                LumoUtility.Padding.Left.SMALL,
                LumoUtility.Padding.Top.SMALL,
                LumoUtility.AlignItems.BASELINE
        );

        Div progressBarWrapper = new Div();
        progressBarWrapper.addClassNames(
                LumoUtility.BoxSizing.BORDER,
                LumoUtility.Position.RELATIVE,
                LumoUtility.TextAlignment.CENTER
        );

        ProgressBar progressBar = new ProgressBar(0, this.maxAmount.doubleValue());
        progressBar.setValue(safeAmount.doubleValue());
        progressBar.addClassName(LumoUtility.Height.XSMALL);
        progressBarWrapper.add(progressBar);

        StringBuilder progressTextBuilder = new StringBuilder();
        if (this.isExpense) {
            progressTextBuilder.append("Можно потратить");
        } else {
            progressTextBuilder.append("Осталось получить");
        }
        progressTextBuilder
                .append(" ")
                .append(this.amountFormatter.apply(this.remainingAmount))
                .append(" до ")
                .append(this.dateFormatter.apply(this.endDate));
        Span progressSpan = new Span(progressTextBuilder.toString());
        progressSpan.addClassNames(
                LumoUtility.FontSize.XSMALL,
                LumoUtility.FontSize.Breakpoint.Medium.SMALL,
                LumoUtility.TextColor.DISABLED
        );

        progressBarWrapper.add(progressSpan);

        if (leftOver.compareTo(BigDecimal.ZERO) > 0) {
            progressBar.addThemeVariants(ProgressBarVariant.LUMO_CONTRAST);
            ProgressBar overflowBar = new ProgressBar(0, this.maxAmount.doubleValue());
            overflowBar.addClassNames(
                    LumoUtility.Position.ABSOLUTE,
                    LumoUtility.Position.Top.NONE,
                    LumoUtility.Height.XSMALL
            );
            overflowBar.addThemeVariants(this.isExpense
                    ? ProgressBarVariant.LUMO_ERROR
                    : ProgressBarVariant.LUMO_SUCCESS
            );
            overflowBar.setValue(leftOver.min(this.maxAmount).doubleValue());

            progressBarWrapper.add(overflowBar);

            StringBuilder overflowText = new StringBuilder(this.typeName)
                    .append(" превышен на ")
                    .append(this.amountFormatter.apply(leftOver))
                    .append(" ");
            if (this.isExpense) {
                overflowText.append("\uD83D\uDE22");
            } else {
                overflowText.append("\uD83D\uDC4D");
            }

            progressSpan.setText(overflowText.toString());
            progressSpan.getElement().getThemeList().add(this.isExpense ? StyleConstants.Badge.ERROR : StyleConstants.Badge.SUCCESS);
        }

        content.addClassName(StyleConstants.CLICKABLE);
        content.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        content.add(amountsLayout, progressBarWrapper);
    }

    @Builder
    public record BudgetCardData(
            String title,
            BigDecimal currentAmount,
            BigDecimal maxAmount,
            BigDecimal remainingAmount,
            LocalDate endDate,
            String typeName,
            boolean isFavourite,
            boolean isExpense,
            Function<BigDecimal, String> amountFormatter,
            Function<LocalDate, String> dateFormatter
    ) {
    }
}
