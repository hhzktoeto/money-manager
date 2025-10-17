package hhz.ktoeto.moneymanager.core.ui.component;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.core.constant.Routes;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
public class NavigationMenu extends Composite<HorizontalLayout> {

    private final Mode mode;

    @Override
    protected HorizontalLayout initContent() {
        HorizontalLayout root = new HorizontalLayout();
        root.addClassNames(rootClassNames());

        Tabs tabs = new Tabs();
        tabs.addThemeVariants(TabsVariant.LUMO_EQUAL_WIDTH_TABS, TabsVariant.LUMO_MINIMAL);
        tabs.addClassNames(
                LumoUtility.BoxSizing.BORDER,
                LumoUtility.Width.FULL,
                LumoUtility.Gap.MEDIUM
        );
        root.add(tabs);

        if (mode == Mode.MOBILE) {
            root.setVisible(false);
            root.setHeight(10, Unit.VH);
        }

        Arrays.stream(Routes.values())
                .filter(Routes::isMenuItem)
                .map(this::createLink)
                .forEach(link -> tabs.add(new Tab(link)));

        return root;
    }

    private RouterLink createLink(Routes route) {
        RouterLink link = new RouterLink(route.getViewClass());
        link.addClassNames(
                LumoUtility.Display.FLEX,
                LumoUtility.Gap.XSMALL,
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.AlignContent.CENTER,
                LumoUtility.JustifyContent.CENTER,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.TextAlignment.CENTER
        );

        Icon icon = route.getIcon().create();
        icon.addClassName(LumoUtility.IconSize.MEDIUM);
        link.add(icon);

        Span routeNameSpan = new Span(route.getName());
        routeNameSpan.addClassName(LumoUtility.FontSize.XXSMALL);
        link.add(routeNameSpan);

        return link;
    }

    private String[] rootClassNames() {
        return switch (mode) {
            case DESKTOP -> new String[]{
                    LumoUtility.Gap.XLARGE,
                    LumoUtility.AlignSelf.CENTER
            };
            case MOBILE -> new String[]{
                    LumoUtility.ZIndex.LARGE,
                    LumoUtility.Gap.MEDIUM,
                    LumoUtility.Position.FIXED,
                    LumoUtility.Position.Bottom.NONE,
                    LumoUtility.AlignItems.START,
                    LumoUtility.Padding.Top.XSMALL,
                    LumoUtility.Width.FULL,
                    LumoUtility.Background.SHADE
            };
        };
    }

    public enum Mode {
        DESKTOP,
        MOBILE
    }
}

