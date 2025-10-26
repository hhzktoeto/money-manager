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
import hhz.ktoeto.moneymanager.core.constant.StyleConstants;
import hhz.ktoeto.moneymanager.feature.HomeView;
import hhz.ktoeto.moneymanager.ui.FormView;
import hhz.ktoeto.moneymanager.ui.FormViewPresenter;
import hhz.ktoeto.moneymanager.ui.component.NavigationMenu;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;

@UIScope
@SpringComponent
public class MainLayout extends VerticalLayout implements RouterLayout {

    private final NavigationMenu desktopNavigation;
    private final NavigationMenu mobileNavigation;
    private final HorizontalLayout header;
    private final VerticalLayout content;

    private final Button addTransactionButtonDesktop;
    private final Button addTransactionButtonMobile;
    private final Image appLogo;

    public MainLayout(FormViewPresenter<Transaction, FormView<Transaction>> transactionFormPresenter) {
        ComponentEventListener<ClickEvent<Button>> openTransactionCreatingModal = e ->
                transactionFormPresenter.openCreateForm();
        this.setPadding(false);
        this.setSpacing(false);
        this.setSizeFull();
        this.setAlignItems(FlexComponent.Alignment.STRETCH);

        header = new HorizontalLayout();
        header.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.MaxWidth.SCREEN_XLARGE,
                LumoUtility.AlignSelf.CENTER,
                LumoUtility.Border.BOTTOM,
                LumoUtility.BorderRadius.LARGE,
                LumoUtility.BorderColor.PRIMARY,
                LumoUtility.Background.TRANSPARENT,
                LumoUtility.Padding.Horizontal.XLARGE,
                LumoUtility.Padding.Vertical.MEDIUM
        );

        appLogo = new Image("/logo.png", "Money Manager");
        appLogo.addClickListener(event -> UI.getCurrent().navigate(HomeView.class));
        appLogo.setWidth(11, Unit.REM);
        appLogo.setClassName(StyleConstants.CLICKABLE);
        header.add(appLogo);

        desktopNavigation = new NavigationMenu(NavigationMenu.Mode.DESKTOP);
        header.add(desktopNavigation);

        addTransactionButtonDesktop = new Button("Добавить транзакцию");
        addTransactionButtonDesktop.addClickListener(openTransactionCreatingModal);
        addTransactionButtonDesktop.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addTransactionButtonDesktop.addClassName(LumoUtility.AlignSelf.CENTER);
        header.add(addTransactionButtonDesktop);

        content = new VerticalLayout();
        content.setSizeFull();
        content.addClassNames(
                LumoUtility.AlignContent.STRETCH,
                LumoUtility.Overflow.AUTO,
                LumoUtility.Padding.Horizontal.NONE
        );

        mobileNavigation = new NavigationMenu(NavigationMenu.Mode.MOBILE);

        addTransactionButtonMobile = new Button(VaadinIcon.PLUS.create());
        addTransactionButtonMobile.addClickListener(openTransactionCreatingModal);
        addTransactionButtonMobile.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addTransactionButtonMobile.getStyle().set("bottom", "11vh");
        addTransactionButtonMobile.addClassNames(
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
        addTransactionButtonMobile.setVisible(false);

        this.add(header, content);
        this.add(addTransactionButtonMobile);
        this.add(mobileNavigation);

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
}
