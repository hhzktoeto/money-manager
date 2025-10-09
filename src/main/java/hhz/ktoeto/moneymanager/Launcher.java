package hhz.ktoeto.moneymanager;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Theme(themeClass = Lumo.class, variant = Lumo.DARK)
@PWA(name = "Money Manager", shortName = "MM", iconPath = "icons/icon-192x192.png")
@Viewport("width=device-width, initial-scale=1")
@SpringBootApplication(scanBasePackages = "hhz.ktoeto.moneymanager")
public class Launcher implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }
}
