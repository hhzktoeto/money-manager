package hhz.ktoeto.moneymanager.ui.feature.user.ui.form;

import com.vaadin.flow.spring.annotation.SpringComponent;
import hhz.ktoeto.moneymanager.ui.feature.user.domain.RegisterRequest;
import hhz.ktoeto.moneymanager.ui.feature.user.domain.User;
import hhz.ktoeto.moneymanager.ui.feature.user.domain.UserService;
import hhz.ktoeto.moneymanager.ui.event.OpenLoginFormEvent;
import hhz.ktoeto.moneymanager.ui.event.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

@SpringComponent
@RequiredArgsConstructor
public class DefaultRegisterFormLogic implements RegisterFormLogic {

    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void onSubmit(RegisterForm form) {
        form.setDisabled(true);
        try {
            RegisterRequest registerRequest = new RegisterRequest();
            boolean valid = form.writeTo(registerRequest);
            if (!valid) {
                return;
            }

            User user = userService.register(registerRequest);

            eventPublisher.publishEvent(new UserRegisteredEvent(this, user));
        } finally {
            form.setDisabled(false);
        }
    }

    @Override
    public void onLogin(RegisterForm form) {
        eventPublisher.publishEvent(new OpenLoginFormEvent(this));
    }
}
