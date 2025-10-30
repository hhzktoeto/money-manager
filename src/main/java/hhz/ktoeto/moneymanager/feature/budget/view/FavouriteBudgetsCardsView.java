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
                LumoUtility.Width.FULL,
                LumoUtility.Gap.MEDIUM,
                LumoUtility.Display.FLEX,
                LumoUtility.FlexDirection.ROW,
                LumoUtility.Overflow.AUTO,
                LumoUtility.JustifyContent.START,
                LumoUtility.AlignItems.START
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
            card.setMinWidth(300, Unit.PIXELS);
            card.setMaxWidth(320, Unit.PIXELS);
            card.addContentClickListener(event -> this.presenter.onEditRequested(card.getBudget()));
            root.add(card);
        });
    }
}
