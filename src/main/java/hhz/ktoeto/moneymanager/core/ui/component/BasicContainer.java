package hhz.ktoeto.moneymanager.core.ui.component;

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
                LumoUtility.Border.BOTTOM,
                LumoUtility.BorderColor.PRIMARY_50,
                LumoUtility.BorderRadius.SMALL,
                LumoUtility.Padding.Bottom.SMALL,
                LumoUtility.Padding.Left.SMALL
        );
        content = new FlexLayout();

        this.addClassNames(
                LumoUtility.Padding.MEDIUM,
                LumoUtility.Border.ALL,
                LumoUtility.BorderRadius.LARGE,
                LumoUtility.BorderColor.PRIMARY_10,
                LumoUtility.Background.SHADE_40,
                LumoUtility.BoxShadow.MEDIUM
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
