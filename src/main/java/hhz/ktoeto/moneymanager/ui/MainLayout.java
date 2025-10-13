package hhz.ktoeto.moneymanager.ui;

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
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.transaction.event.OpenTransactionCreateDialogEvent;
import hhz.ktoeto.moneymanager.ui.view.MainView;
import hhz.ktoeto.moneymanager.utils.RouterUtils;
import hhz.ktoeto.moneymanager.utils.StylingUtils;
import org.springframework.context.ApplicationEventPublisher;

@UIScope
@SpringComponent
public class MainLayout extends VerticalLayout implements RouterLayout {

    private final HorizontalLayout desktopNavigation;
    private final HorizontalLayout mobileNavigation;
    private final HorizontalLayout header;
    private final VerticalLayout content;

    private final Button addTransactionButtonDesktop;
    private final Button addTransactionButtonMobile;
    private final Image appLogo;

    public MainLayout(ApplicationEventPublisher eventPublisher) {
        ComponentEventListener<ClickEvent<Button>> openTransactionCreatingModal = e ->
                eventPublisher.publishEvent(new OpenTransactionCreateDialogEvent(this));

        this.setPadding(false);
        this.setSpacing(false);
        this.setSizeFull();
        this.setAlignItems(FlexComponent.Alignment.STRETCH);

        header = new HorizontalLayout();
        header.setWidth(70, Unit.REM);
        header.addClassNames(
                LumoUtility.AlignSelf.CENTER,
                LumoUtility.Border.BOTTOM,
                LumoUtility.BorderRadius.MEDIUM,
                LumoUtility.BorderColor.PRIMARY,
                LumoUtility.Background.TRANSPARENT,
                LumoUtility.Padding.Horizontal.XLARGE,
                LumoUtility.Padding.Vertical.MEDIUM
        );

        appLogo = new Image("/logo.png", "Money Manager");
        appLogo.addClickListener(event -> UI.getCurrent().navigate(MainView.class));
        appLogo.setWidth(11, Unit.REM);
        appLogo.setClassName(StylingUtils.CLICKABLE);
        header.add(appLogo);

        desktopNavigation = new HorizontalLayout();
        desktopNavigation.add(RouterUtils.desktopRouterLinks().toArray(RouterLink[]::new));
        desktopNavigation.addClassNames(
                LumoUtility.Gap.XLARGE,
                LumoUtility.AlignSelf.CENTER
        );
        header.add(desktopNavigation);

        addTransactionButtonDesktop = new Button("Добавить транзакцию");
        addTransactionButtonDesktop.addClickListener(openTransactionCreatingModal);
        addTransactionButtonDesktop.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addTransactionButtonDesktop.addClassName(LumoUtility.AlignSelf.END);
        header.add(addTransactionButtonDesktop);

        content = new VerticalLayout();
        content.setSizeFull();
        content.addClassNames(
                LumoUtility.AlignContent.STRETCH,
                LumoUtility.Overflow.AUTO,
                LumoUtility.Padding.MEDIUM
        );

        mobileNavigation = new HorizontalLayout();
        mobileNavigation.add(RouterUtils.mobileRouterLinks().toArray(RouterLink[]::new));
        mobileNavigation.setSpacing(true);
        mobileNavigation.setHeight(5.5f, Unit.REM);
        mobileNavigation.addClassNames(
                LumoUtility.Gap.XLARGE,
                LumoUtility.Position.FIXED,
                LumoUtility.Position.Bottom.NONE,
                LumoUtility.Width.FULL,
                LumoUtility.Height.LARGE,
                LumoUtility.JustifyContent.CENTER,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.FontSize.XLARGE,
                LumoUtility.Padding.SMALL,
                LumoUtility.Border.TOP,
                LumoUtility.Background.SHADE
        );
        mobileNavigation.setVisible(false);

        addTransactionButtonMobile = new Button(VaadinIcon.PLUS.create());
        addTransactionButtonMobile.addClickListener(openTransactionCreatingModal);
        addTransactionButtonMobile.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addTransactionButtonMobile.getStyle().set("bottom", "6rem");
        addTransactionButtonMobile.addClassNames(
                LumoUtility.Position.FIXED,
                LumoUtility.Position.End.SMALL,
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

            content.addClassName(LumoUtility.Padding.Bottom.XLARGE);
        } else {
            desktopNavigation.setVisible(true);
            addTransactionButtonDesktop.setVisible(true);

            mobileNavigation.setVisible(false);
            addTransactionButtonMobile.setVisible(false);

            header.setJustifyContentMode(JustifyContentMode.BETWEEN);
            header.removeClassName(LumoUtility.TextAlignment.CENTER);

            content.removeClassName(LumoUtility.Padding.Bottom.XLARGE);
        }
    }
}
