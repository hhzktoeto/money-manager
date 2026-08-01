package hhz.ktoeto.moneymanager.core.security;

import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import hhz.ktoeto.moneymanager.feature.login.LoginRouteView;
import hhz.ktoeto.moneymanager.feature.login.domain.UserService;
import hhz.ktoeto.moneymanager.ui.constant.Routes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return username -> userService.getByUsername(username)
                .map(AppUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(JdbcTemplate jdbcTemplate) {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setJdbcTemplate(jdbcTemplate);

        return repository;
    }

    @Bean
    public RememberMeServices rememberMeServices(UserDetailsService userDetailsService, PersistentTokenRepository persistentTokenRepository,
            @Value("${spring.security.remember-me.key}") String rememberMeKey,
            @Value("${spring.security.remember-me.cookie-name}") String rememberMeCookieName,
            @Value("${spring.security.remember-me.max-age}") Integer rememberMeMaxAge) {
        PersistentTokenBasedRememberMeServices services = new PersistentTokenBasedRememberMeServices(rememberMeKey, userDetailsService, persistentTokenRepository);
        services.setCookieName(rememberMeCookieName);
        services.setTokenValiditySeconds(rememberMeMaxAge);
        services.setAlwaysRemember(true);
        services.setUseSecureCookie(false);
        return services;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, RememberMeServices rememberMeServices) {
        return http
                .with(VaadinSecurityConfigurer.vaadin(), configurer -> configurer
                        .loginView(LoginRouteView.class)
                        .defaultSuccessUrl(Routes.Path.HOME, true))
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                        "/logo.png",
                        "/categories/*.png",
                        "/icons/**",
                        "/nord/**"
                ).permitAll())
                .rememberMe(configurer -> configurer.rememberMeServices(rememberMeServices))
                .build();
    }
}
