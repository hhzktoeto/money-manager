package hhz.ktoeto.moneymanager;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Theme("default")
@SpringBootApplication(scanBasePackages = "hhz.ktoeto.moneymanager")
@PWA(name = "Money Manager", shortName = "MM")
public class Launcher implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Launcher.class, args);
    }

}
