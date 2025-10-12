package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.Getter;

@Getter
public class BasicContainer extends FlexLayout {

    private final FlexLayout header;
    private final FlexLayout content;

    public BasicContainer() {
        this.setFlexDirection(FlexDirection.COLUMN);
        header = new FlexLayout();
        content = new FlexLayout();

        this.addClassNames(
                LumoUtility.Padding.MEDIUM,
                LumoUtility.BorderRadius.LARGE,
                LumoUtility.Border.ALL,
                LumoUtility.BorderColor.PRIMARY,
                LumoUtility.Background.TINT_5,
                LumoUtility.BoxShadow.LARGE
        );

        this.add(this.header, this.content);
    }

    public BasicContainer(String header) {
        this();
        this.setHeader(header);
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
}
