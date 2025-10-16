package hhz.ktoeto.moneymanager.core.ui.component;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.HighlightActions;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.core.constant.Routes;

import java.util.Arrays;

public class NavigationMenu extends Composite<HorizontalLayout> {

    public enum Mode {
        DESKTOP,
        MOBILE
    }

    private final Mode mode;

    public NavigationMenu(Mode mode) {
        this.mode = mode;
    }

    @Override
    protected HorizontalLayout initContent() {
        HorizontalLayout root = new HorizontalLayout();
        root.addClassNames(classNames());

        if (mode == Mode.MOBILE) {
            root.setSpacing(true);
            root.setHeight(10, Unit.VH);
            root.setVisible(false);
        }

        RouterLink[] links = Arrays.stream(Routes.values())
                .filter(Routes::isMenuItem)
                .map(this::createLink)
                .toArray(RouterLink[]::new);
        root.add(links);

        return root;
    }

    private RouterLink createLink(Routes route) {
        RouterLink link = new RouterLink(route.getViewClass());
        link.setHighlightCondition(HighlightConditions.sameLocation());
        link.setHighlightAction(HighlightActions.toggleClassName(LumoUtility.TextColor.BODY));

        link.addClassNames(
                LumoUtility.Display.FLEX,
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.TextAlignment.CENTER,
                mode == Mode.DESKTOP
                        ? LumoUtility.FontSize.MEDIUM
                        : LumoUtility.FontSize.XSMALL
        );

        link.add(route.getIcon().create());
        link.add(route.getName());

        return link;
    }

    private String[] classNames() {
        return switch (mode) {
            case DESKTOP -> new String[] {
                    LumoUtility.Gap.XLARGE,
                    LumoUtility.AlignSelf.CENTER
            };
            case MOBILE -> new String[] {
                    LumoUtility.Gap.XLARGE,
                    LumoUtility.Position.FIXED,
                    LumoUtility.Position.Bottom.NONE,
                    LumoUtility.Width.FULL,
                    LumoUtility.Height.LARGE,
                    LumoUtility.JustifyContent.BETWEEN,
                    LumoUtility.AlignItems.START,
                    LumoUtility.Padding.Top.SMALL,
                    LumoUtility.Border.TOP,
                    LumoUtility.Padding.Horizontal.LARGE,
                    LumoUtility.Background.SHADE
            } ;
        };
    }
}
