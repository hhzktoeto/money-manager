package hhz.ktoeto.moneymanager.ui.layout;

import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.HomeView;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionFormViewPresenter;
import hhz.ktoeto.moneymanager.ui.component.NavigationMenu;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;

@UIScope
@SpringComponent
public class MainLayout extends VerticalLayout implements RouterLayout {

    private final TransactionFormViewPresenter transactionFormPresenter;

    private final NavigationMenu desktopNavigation;
    private final NavigationMenu mobileNavigation;
    private final HorizontalLayout header;
    private final VerticalLayout content;

    private final Button addTransactionButtonDesktop;
    private final Button addTransactionButtonMobile;
    private final Image appLogo;

    public MainLayout(TransactionFormViewPresenter transactionFormPresenter) {
        this.transactionFormPresenter = transactionFormPresenter;

        this.appLogo = new Image("/logo.png", "Money Manager");
        this.desktopNavigation = new NavigationMenu(NavigationMenu.Mode.DESKTOP);
        this.addTransactionButtonDesktop = new Button("Добавить транзакцию");
        this.header = new HorizontalLayout(this.appLogo, this.desktopNavigation, this.addTransactionButtonDesktop);

        this.content = new VerticalLayout();

        this.mobileNavigation = new NavigationMenu(NavigationMenu.Mode.MOBILE);
        this.addTransactionButtonMobile = new Button(VaadinIcon.PLUS.create());

        this.configureHeader();
        this.configureAppLogo();
        this.configureAddTransactionButtonDesktop();
        this.configureContent();
        this.configureAddTransactionButtonMobile();

        this.setPadding(false);
        this.setSpacing(false);
        this.setSizeFull();
        this.setAlignItems(FlexComponent.Alignment.STRETCH);
        this.add(header, content, addTransactionButtonMobile, mobileNavigation);
        this.addAttachListener(attachEvent -> {
            Page page = attachEvent.getUI().getPage();
            page.addBrowserWindowResizeListener(resizeEvent -> updateResponsive(resizeEvent.getWidth()));
            page.retrieveExtendedClientDetails(details -> updateResponsive(details.getWindowInnerWidth()));
        });
    }

    @Override
    public void showRouterLayoutContent(HasElement content) {
        if (content != null) {
            this.content.getElement().removeAllChildren();
            this.content.getElement().appendChild(content.getElement());
        }
    }

    private void updateResponsive(int screenWidth) {
        if (screenWidth < 1024) {
            desktopNavigation.setVisible(false);
            addTransactionButtonDesktop.setVisible(false);

            mobileNavigation.setVisible(true);
            addTransactionButtonMobile.setVisible(true);

            header.setJustifyContentMode(JustifyContentMode.CENTER);
            header.addClassName(LumoUtility.TextAlignment.CENTER);

            content.getStyle().set("margin-bottom", "10vh");
        } else {
            desktopNavigation.setVisible(true);
            addTransactionButtonDesktop.setVisible(true);

            mobileNavigation.setVisible(false);
            addTransactionButtonMobile.setVisible(false);

            header.setJustifyContentMode(JustifyContentMode.BETWEEN);
            header.removeClassName(LumoUtility.TextAlignment.CENTER);

            content.getStyle().remove("margin-bottom");
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
        this.appLogo.addClickListener(event -> UI.getCurrent().navigate(HomeView.class));
        this.appLogo.setWidth(11, Unit.REM);
        this.appLogo.setHeightFull();
        this.appLogo.setMaxHeight(3.25f, Unit.REM);
        this.appLogo.setClassName(StyleConstants.CLICKABLE);
    }

    private void configureAddTransactionButtonDesktop() {
        this.addTransactionButtonDesktop.addClickListener(e -> this.transactionFormPresenter.openCreateForm());
        this.addTransactionButtonDesktop.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        this.addTransactionButtonDesktop.addClassName(LumoUtility.AlignSelf.CENTER);
    }

    private void configureContent() {
        this.content.addClassNames(
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
        this.addTransactionButtonMobile.addClickListener(e -> this.transactionFormPresenter.openCreateForm());
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
