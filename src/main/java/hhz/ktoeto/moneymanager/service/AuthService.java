package hhz.ktoeto.moneymanager.service;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;
import hhz.ktoeto.moneymanager.utils.RouteName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;

    public void login(String username, String password) throws AuthenticationException {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    public void logout() {
        SecurityContextHolder.clearContext();
        VaadinSession.getCurrent().close();
        UI.getCurrent().getPage().setLocation(RouteName.LOGIN);
    }
}
