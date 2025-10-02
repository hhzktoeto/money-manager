package hhz.ktoeto.moneymanager.utils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.validation.constraints.NotNull;
import org.springframework.lang.Nullable;

public final class RouterUtils {

    private RouterUtils() {
    }

    public static RouterLink createLink(String viewName, Class<? extends Component> viewClass) {
        return doCreateLink(viewName, viewClass, null);
    }

    public static RouterLink createLink(String viewName, Class<? extends Component> viewClass, @NotNull VaadinIcon icon) {
        return doCreateLink(viewName, viewClass, icon);
    }

    private static RouterLink doCreateLink(String viewName, Class<? extends Component> viewClass, @Nullable VaadinIcon icon) {
        RouterLink link = new RouterLink(viewName, viewClass);
        if (icon != null) {
            link.add(icon.create());
        }
        link.addClassNames(
                LumoUtility.Display.FLEX,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.Padding.Horizontal.MEDIUM
        );

        return link;
    }
}
