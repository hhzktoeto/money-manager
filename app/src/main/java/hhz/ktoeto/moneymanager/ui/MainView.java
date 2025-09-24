package hhz.ktoeto.moneymanager.ui;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Route;
import hhz.ktoeto.moneymanager.ui.component.Dashboard;
import hhz.ktoeto.moneymanager.ui.component.LoginCard;
import hhz.ktoeto.moneymanager.utils.SecurityUtils;

@Route(value = "", layout = MainLayout.class)
@CssImport("themes/default/styles.css")
public class MainView extends VerticalLayout {

    private final ProgressBar spinner = new ProgressBar();
    private final Div contentHolder = new Div();

    public MainView() {
        setSizeFull();
        addClassName("main-root");
        spinner.setIndeterminate(true);
        spinner.addClassName("main-spinner");

        contentHolder.setSizeFull();
        contentHolder.addClassName("content-holder");

        // При старте: показать spinner (если вам нужно асинхронно загрузить — тут можно)
        updateUI(false);
    }

    /**
     * Обновляет UI: если loading -> показываем spinner; иначе смотрим auth -> dashboard или login card.
     * @param loading true при асинхронной загрузке
     */
    public void updateUI(boolean loading) {
        removeAll();
        if (loading) {
            add(spinner);
        } else {
            if (SecurityUtils.isUserLoggedIn()) {
                add(new Dashboard());
                Notification.show("Привет, " + (SecurityUtils.getUsername() == null ? "" : SecurityUtils.getUsername()),
                        2500, Position.BOTTOM_CENTER);
            } else {
                // Передаём коллбек, чтобы после успешной авторизации обновить UI
                LoginCard loginCard = new LoginCard(() -> {
                    // после условной успешной авторизации
                    getUI().ifPresent(ui -> ui.getPage().reload()); // для простоты полная перезагрузка
                });
                add(loginCard);
            }
        }
    }
}
