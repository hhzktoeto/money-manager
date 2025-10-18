package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.H3;
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
        header.addClassNames(
                LumoUtility.BoxSizing.BORDER,
                LumoUtility.Padding.MEDIUM,
                LumoUtility.Padding.Bottom.NONE,
                LumoUtility.Width.FULL
        );

        content = new FlexLayout();
        content.addClassNames(
                LumoUtility.BoxSizing.BORDER,
                LumoUtility.Width.FULL,
                LumoUtility.Padding.SMALL,
                LumoUtility.Padding.Top.NONE
        );

        this.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.BoxSizing.BORDER,
                LumoUtility.Border.ALL,
                LumoUtility.BorderRadius.LARGE,
                LumoUtility.BorderColor.PRIMARY_10,
                LumoUtility.Background.SHADE,
                LumoUtility.BoxShadow.LARGE
        );

        this.add(this.header, this.content);
    }

    public void setHeader(String text) {
        this.header.removeAll();
        this.header.add(new H3(text));
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
