package hhz.ktoeto.moneymanager.utils;

import hhz.ktoeto.moneymanager.core.security.AppUserDetails;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public final class SecurityUtils {

    public static Long getCurrentUserId() {
        return getCurrentUser().orElseThrow().getId();
    }

    private static Optional<AppUserDetails> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        Object principal = authentication.getPrincipal();

        return principal instanceof AppUserDetails ? Optional.of((AppUserDetails) principal) : Optional.empty();
    }
}

