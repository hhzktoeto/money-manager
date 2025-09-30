package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.theme.lumo.LumoUtility;

public abstract class ComponentContainer extends Div {

    protected final Div header = new Div();
    protected final Div content = new Div();

    protected ComponentContainer() {
        this.header.addClassName(LumoUtility.Margin.Bottom.SMALL);
        this.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Background.TINT_10,
                LumoUtility.BorderRadius.LARGE,
                LumoUtility.BoxShadow.MEDIUM,
                LumoUtility.Padding.MEDIUM,
                LumoUtility.Display.FLEX,
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Gap.MEDIUM,
                LumoUtility.Margin.Bottom.XLARGE
        );
        this.add(this.header, this.content);
    }

    public void setHeader(String text) {
        this.header.removeAll();
        this.header.add(new H2(text));
    }

    public void setHeader(Component component) {
        this.header.removeAll();
        this.header.add(component);
    }

    public void setContent(Component... components) {
        this.content.removeAll();
        this.content.add(components);
    }

    public void addContentClassName(String className) {
        this.content.addClassName(className);
    }
}
