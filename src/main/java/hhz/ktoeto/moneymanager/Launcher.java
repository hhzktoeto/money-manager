package hhz.ktoeto.moneymanager;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.ColorScheme;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Viewport("width=device-width, initial-scale=1")
@ColorScheme(ColorScheme.Value.DARK)
@StyleSheet(Lumo.STYLESHEET)
@StyleSheet(Lumo.UTILITY_STYLESHEET)
@StyleSheet("nord/styles.css")
@PWA(
        name = "Money Manager",
        shortName = "MM",
        iconPath = "icons/icon-192x192.png"
)
@EnableScheduling
@SpringBootApplication
public class Launcher implements AppShellConfigurator {

    static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }
}
