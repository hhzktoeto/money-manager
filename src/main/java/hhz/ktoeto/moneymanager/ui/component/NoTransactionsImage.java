package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class NoTransactionsImage extends Composite<VerticalLayout> {

    private Image image = new Image("no_transactions.png","Пусто");
    private Span text = new Span();

    @Override
    protected VerticalLayout initContent() {
        VerticalLayout root = new VerticalLayout();
        root.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.CENTER);

        image.setWidth(50, Unit.PERCENTAGE);
        text.addClassName(LumoUtility.TextColor.DISABLED);

        root.add(image, text);

        return root;
    }

    public void setText(String text) {
        this.text.setText(text);
    }
}
