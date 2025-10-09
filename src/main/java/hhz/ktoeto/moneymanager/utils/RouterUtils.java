package hhz.ktoeto.moneymanager.utils;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public final class RouterUtils {

    private RouterUtils() {
    }

    public static RouterLink createLink(@NonNull Class<? extends Component> viewClass, @NonNull Icon icon) {
        return doCreateLink(null, viewClass, icon);
    }

    public static RouterLink createLink(@NonNull Class<? extends Component> viewClass, @NonNull String viewName) {
        return doCreateLink(viewName, viewClass, null);
    }

    public static RouterLink createLink(@NonNull Class<? extends Component> viewClass, @NonNull String viewName, @NonNull Icon icon) {
        return doCreateLink(viewName, viewClass, icon);
    }

    private static RouterLink doCreateLink(@Nullable String viewName, @NonNull Class<? extends Component> viewClass, @Nullable Icon icon) {
        RouterLink link = new RouterLink(viewClass);
        if (icon != null) {
            link.add(icon);
        }
        if (viewName != null) {
            link.add(viewName);
        }

        return link;
    }
}
