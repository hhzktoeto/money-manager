package hhz.ktoeto.moneymanager.utils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;

public final class RouterUtils {

    private RouterUtils() {}

    public static RouterLink createLink(String viewName, Class<? extends Component> viewClass, VaadinIcon icon) {
        RouterLink link = new RouterLink(viewName, viewClass);
        link.add(icon.create());
        link.addClassNames(
                LumoUtility.Display.FLEX,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.Padding.Horizontal.MEDIUM
        );

        return link;
    }
}
