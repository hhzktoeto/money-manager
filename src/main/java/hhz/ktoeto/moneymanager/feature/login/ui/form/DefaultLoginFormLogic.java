package hhz.ktoeto.moneymanager.feature.login.ui.form;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinServletResponse;
import com.vaadin.flow.spring.annotation.SpringComponent;
import hhz.ktoeto.moneymanager.core.event.OpenRegisterFormEvent;
import hhz.ktoeto.moneymanager.feature.login.domain.LoginRequest;
import hhz.ktoeto.moneymanager.ui.constant.Routes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

@Slf4j
@SpringComponent
@RequiredArgsConstructor
public class DefaultLoginFormLogic implements LoginFormLogic {

    private final ApplicationEventPublisher eventPublisher;
    private final AuthenticationManager authenticationManager;
    private final RememberMeServices rememberMeServices;

    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    @Override
    public void onSubmit(LoginForm form) {
        form.setDisabled(true);
        try {
            LoginRequest loginRequest = new LoginRequest();
            if (!form.writeTo(loginRequest)) {
                return;
            }

            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword());

            Authentication authentication = authenticationManager.authenticate(authRequest);

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);

            HttpServletRequest request = VaadinServletRequest.getCurrent().getHttpServletRequest();
            HttpServletResponse response = VaadinServletResponse.getCurrent().getHttpServletResponse();
            securityContextRepository.saveContext(context, request, response);
            rememberMeServices.loginSuccess(request, response, authentication);

            UI.getCurrent().navigate(Routes.Path.HOME);
        } catch (BadCredentialsException _) {
            form.showErrorMessage("Неверный логин или пароль", "Попробуйте ещё раз");
        } catch (Exception ex) {
            log.error("Login failed", ex);
            form.showErrorMessage("Ошибка входа", "Попробуйте ещё раз позже");
        } finally {
            form.setDisabled(false);
        }
    }

    @Override
    public void onRegister(LoginForm form) {
        eventPublisher.publishEvent(new OpenRegisterFormEvent(this));
    }
}
