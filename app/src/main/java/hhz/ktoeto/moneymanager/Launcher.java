package hhz.ktoeto.moneymanager;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Theme(value = "nord", variant = "dark")
@PWA(name = "Money Manager", shortName = "MM")
@Viewport("width=device-width, initial-scale=1")
@SpringBootApplication(scanBasePackages = "hhz.ktoeto.moneymanager")
public class Launcher implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }

}
