package hhz.ktoeto.moneymanager.utils;

import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static boolean isUserLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken);
    }

    public static String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return principal != null ? auth.getName() : null;
    }

    public static void logout() {
        // Invalidate session (Vaadin + Spring Security)
        VaadinServletRequest vsr = (VaadinServletRequest) VaadinService.getCurrentRequest();
        if (vsr != null) {
            HttpServletRequest req = vsr.getHttpServletRequest();
            HttpSession session = req.getSession(false);
            if (session != null) {
                session.invalidate();
            }
        }
        SecurityContextHolder.clearContext();
        // After logout, reload page to show login
        Page page = com.vaadin.flow.component.UI.getCurrent().getPage();
        page.reload();
    }
}

