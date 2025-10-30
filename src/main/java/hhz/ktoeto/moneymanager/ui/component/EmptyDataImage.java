package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class EmptyDataImage extends Composite<VerticalLayout> {

    private final Image image = new Image("empty_data.png", "Пусто");
    private final Span text = new Span();

    @Override
    protected VerticalLayout initContent() {
        VerticalLayout root = new VerticalLayout();
        root.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.CENTER
        );

        this.image.setWidth(40, Unit.PERCENTAGE);
        this.text.addClassName(LumoUtility.TextColor.DISABLED);

        root.add(this.image, this.text);

        return root;
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setImageMaxWidth(float width, Unit unit) {
        this.image.setMaxWidth(width, unit);
    }
}
