package hhz.ktoeto.moneymanager.feature.user.ui.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import hhz.ktoeto.moneymanager.feature.user.domain.LoginRequest;
import hhz.ktoeto.moneymanager.core.event.OpenRegisterFormEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@SpringComponent
@RequiredArgsConstructor
public class DefaultLoginFormLogic implements LoginFormLogic {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void onSubmit(LoginForm form) {
        form.setDisabled(true);
        try {
            LoginRequest loginRequest = new LoginRequest();
            boolean valid = form.writeTo(loginRequest);
            if (!valid) {
                return;
            }

            LoginForm.Components components = form.components();

            components.usernameInput().setValue(loginRequest.getLogin());
            components.passwordInput().setValue(loginRequest.getPassword());
            components.hiddenForm().getElement().callJsFunction("submit");
        } finally {
            form.setDisabled(false);
        }
    }

    @Override
    public void onRegister(LoginForm form) {
        eventPublisher.publishEvent(new OpenRegisterFormEvent(this));
    }
}
