package hhz.ktoeto.moneymanager;

import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import hhz.ktoeto.moneymanager.ui.component.CustomDialog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Viewport("width=device-width, initial-scale=1")
@Theme(
        value = "nord",
        variant = "dark"
)
@PWA(
        name = "Money Manager",
        shortName = "MM",
        iconPath = "icons/icon-192x192.png"
)
@Uses(CustomDialog.class)
@EnableScheduling
@SpringBootApplication
public class Launcher implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }
}
