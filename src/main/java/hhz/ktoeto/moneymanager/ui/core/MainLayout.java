package hhz.ktoeto.moneymanager.ui.core;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.home.HomeRouteView;
import hhz.ktoeto.moneymanager.ui.component.NavigationMenu;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;
import hhz.ktoeto.moneymanager.ui.event.TransactionCreateRequested;
import org.springframework.context.ApplicationEventPublisher;
import org.vaadin.addons.gl0b3.materialicons.MaterialIcons;

@UIScope
@SpringComponent
public class MainLayout extends FlexLayout implements RouterLayout {

    private final transient ApplicationEventPublisher eventPublisher;

    private final NavigationMenu desktopNavigation;
    private final NavigationMenu mobileNavigation;
    private final HorizontalLayout header;
    private final VerticalLayout contentContainer;

    private final Button addTransactionButtonDesktop;
    private final Button addTransactionButtonMobile;
    private final Image appLogo;

    public MainLayout(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;

        this.appLogo = new Image("/logo.png", "Money Manager");
        this.desktopNavigation = new NavigationMenu(NavigationMenu.Mode.DESKTOP);
        this.addTransactionButtonDesktop = new Button("Добавить транзакцию");
        this.header = new HorizontalLayout(this.appLogo, this.desktopNavigation, this.addTransactionButtonDesktop);
        this.contentContainer = new VerticalLayout();

        this.mobileNavigation = new NavigationMenu(NavigationMenu.Mode.MOBILE);
        this.addTransactionButtonMobile = new Button(MaterialIcons.ADD.create());

        this.configureHeader();
        this.configureAppLogo();
        this.configureAddTransactionButtonDesktop();
        this.configureContentContainer();
        this.configureAddTransactionButtonMobile();

        this.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Height.FULL,
                LumoUtility.FlexDirection.COLUMN,
                LumoUtility.Padding.NONE,
                LumoUtility.AlignItems.STRETCH
        );
        this.add(
                this.header,
                this.contentContainer,
                this.addTransactionButtonMobile,
                this.mobileNavigation
        );
        this.addAttachListener(attachEvent -> {
            Page page = attachEvent.getUI().getPage();
            page.addBrowserWindowResizeListener(resizeEvent -> this.updateResponsive(resizeEvent.getWidth()));
            page.retrieveExtendedClientDetails(details -> this.updateResponsive(details.getWindowInnerWidth()));
        });
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        if (content != null) {
            this.contentContainer.getElement().removeAllChildren();
            this.contentContainer.getElement().appendChild(content.getElement());
        }

        this.desktopNavigation.highlightSelected();
        this.mobileNavigation.highlightSelected();
    }

    private void updateResponsive(int screenWidth) {
        if (screenWidth < 1024) {
            this.desktopNavigation.setVisible(false);
            this.addTransactionButtonDesktop.setVisible(false);

            this.mobileNavigation.setVisible(true);
            this.addTransactionButtonMobile.setVisible(true);

            this.header.setJustifyContentMode(JustifyContentMode.CENTER);
            this.header.addClassName(LumoUtility.TextAlignment.CENTER);

            this.contentContainer.getStyle().set("margin-bottom", "10vh");
        } else {
            this.desktopNavigation.setVisible(true);
            this.addTransactionButtonDesktop.setVisible(true);

            this.mobileNavigation.setVisible(false);
            this.addTransactionButtonMobile.setVisible(false);

            this.header.setJustifyContentMode(JustifyContentMode.BETWEEN);
            this.header.removeClassName(LumoUtility.TextAlignment.CENTER);

            this.contentContainer.getStyle().remove("margin-bottom");
        }
    }

    private void configureHeader() {
        this.header.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.MaxWidth.SCREEN_XLARGE,
                LumoUtility.AlignSelf.CENTER,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.Border.BOTTOM,
                LumoUtility.BorderRadius.LARGE,
                LumoUtility.BorderColor.PRIMARY,
                LumoUtility.Background.TRANSPARENT,
                LumoUtility.Padding.Horizontal.XLARGE,
                LumoUtility.Padding.Vertical.MEDIUM
        );
    }

    private void configureAppLogo() {
        this.appLogo.addClickListener(event -> UI.getCurrent().navigate(HomeRouteView.class));
        this.appLogo.setWidth(11, Unit.REM);
        this.appLogo.setHeightFull();
        this.appLogo.setMaxHeight(3.25f, Unit.REM);
        this.appLogo.setClassName(StyleConstants.CLICKABLE);
    }

    private void configureAddTransactionButtonDesktop() {
        this.addTransactionButtonDesktop.addClickListener(e -> this.eventPublisher.publishEvent(new TransactionCreateRequested(this)));
        this.addTransactionButtonDesktop.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.addTransactionButtonDesktop.addClassName(LumoUtility.AlignSelf.CENTER);
    }

    private void configureContentContainer() {
        this.contentContainer.addClassNames(
                LumoUtility.Padding.Horizontal.MEDIUM,
                LumoUtility.Padding.Vertical.MEDIUM,
                LumoUtility.Width.FULL,
                LumoUtility.MaxWidth.SCREEN_LARGE,
                LumoUtility.AlignSelf.CENTER,
                LumoUtility.Height.FULL,
                LumoUtility.AlignContent.STRETCH,
                LumoUtility.Overflow.AUTO
        );
    }

    private void configureAddTransactionButtonMobile() {
        this.addTransactionButtonMobile.addClickListener(e -> this.eventPublisher.publishEvent(new TransactionCreateRequested(this)));
        this.addTransactionButtonMobile.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.addTransactionButtonMobile.getStyle().set("bottom", "11vh");
        this.addTransactionButtonMobile.addClassNames(
                LumoUtility.Position.FIXED,
                LumoUtility.Position.End.MEDIUM,
                LumoUtility.Width.XLARGE,
                LumoUtility.Height.XLARGE,
                LumoUtility.BoxShadow.MEDIUM,
                LumoUtility.ZIndex.XLARGE,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.CENTER,
                LumoUtility.Transition.TRANSFORM
        );
        this.addTransactionButtonMobile.setVisible(false);
    }
}
