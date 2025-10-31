package hhz.ktoeto.moneymanager.feature.budget.view;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.component.BudgetCard;
import hhz.ktoeto.moneymanager.ui.component.EmptyDataImage;

import java.util.List;

public class FavouriteBudgetsCardsView extends BudgetsCardsView {

    protected FavouriteBudgetsCardsView(FavouriteBudgetsCardsPresenter presenter) {
        super(presenter);
    }

    @Override
    protected Div initContent() {
        Div root = new Div();
        root.addClassNames(
                LumoUtility.Display.GRID,
                LumoUtility.Grid.FLOW_COLUMN,
                LumoUtility.Grid.Row.ROWS_1,
                LumoUtility.Flex.SHRINK_NONE,
                LumoUtility.Width.FULL,
                LumoUtility.Gap.MEDIUM,
                LumoUtility.Overflow.AUTO,
                LumoUtility.AlignContent.START,
                LumoUtility.AlignItems.STRETCH
        );

        return root;
    }

    @Override
    public void update(List<BudgetCard> data) {
        Div root = this.getContent();
        root.removeAll();

        if (data.isEmpty()) {
            EmptyDataImage emptyDataImage = new EmptyDataImage();
            emptyDataImage.setText("Здесь будут избранные бюджеты");
            emptyDataImage.setImageMaxWidth(13, Unit.REM);

            root.add(emptyDataImage);
            return;
        }

        data.forEach(card -> {
            card.setMinWidth(18, Unit.REM);
            card.setMaxWidth(25, Unit.REM);
            card.addContentClickListener(event -> this.getPresenter().onEditRequested(card.getBudget()));
            root.add(card);
        });
    }
}
