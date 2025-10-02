package hhz.ktoeto.moneymanager.utils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public final class RouterUtils {

    private RouterUtils() {
    }

    public static RouterLink createLink(@NonNull Class<? extends Component> viewClass, @NonNull VaadinIcon icon) {
        return doCreateLink(null, viewClass, icon);
    }

    public static RouterLink createLink(@NonNull Class<? extends Component> viewClass, @NonNull String viewName) {
        return doCreateLink(viewName, viewClass, null);
    }

    public static RouterLink createLink(@NonNull Class<? extends Component> viewClass, @NonNull String viewName, @NonNull VaadinIcon icon) {
        return doCreateLink(viewName, viewClass, icon);
    }

    private static RouterLink doCreateLink(@Nullable String viewName, @NonNull Class<? extends Component> viewClass, @Nullable VaadinIcon icon) {
        RouterLink link = new RouterLink(viewClass);
        if (icon != null) {
            link.add(icon.create());
        }
        if (viewName != null) {
            link.add(viewName);
        }

        link.addClassNames(
                LumoUtility.Display.FLEX,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.Padding.Horizontal.MEDIUM
        );

        return link;
    }
}
