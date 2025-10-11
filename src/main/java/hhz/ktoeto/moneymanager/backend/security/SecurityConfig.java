package hhz.ktoeto.moneymanager.backend.security;

import com.vaadin.flow.spring.security.VaadinAwareSecurityContextHolderStrategyConfiguration;
import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import hhz.ktoeto.moneymanager.backend.service.UserService;
import hhz.ktoeto.moneymanager.ui.view.LoginView;
import hhz.ktoeto.moneymanager.utils.CookieConstant;
import hhz.ktoeto.moneymanager.utils.RouterUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
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
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Import(VaadinAwareSecurityContextHolderStrategyConfiguration.class)
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
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource) {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);

        return repository;
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);

        return new ProviderManager(provider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           UserDetailsService userDetailsService,
                                           PersistentTokenRepository persistentTokenRepository,
                                           @Value("${spring.security.remember-me}") String secret) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(null);

        return http
                .with(VaadinSecurityConfigurer.vaadin(), configurer -> configurer.loginView(LoginView.class, RouterUtils.RouteName.LOGIN))
                .authorizeHttpRequests(auth -> auth.requestMatchers("/logo.png").permitAll())
                .requestCache(configurer -> configurer.requestCache(requestCache))
                .formLogin(configurer -> configurer
                        .loginPage(RouterUtils.RouteName.LOGIN)
                        .loginProcessingUrl(RouterUtils.RouteName.LOGIN)
                        .defaultSuccessUrl(RouterUtils.RouteName.MAIN, true)
                )
                .rememberMe(configurer -> configurer
                        .key(secret)
                        .userDetailsService(userDetailsService)
                        .tokenRepository(persistentTokenRepository)
                        .rememberMeCookieName(CookieConstant.REMEMBER_ME)
                        .tokenValiditySeconds(CookieConstant.REMEMBER_ME_MAX_AGE)
                        .useSecureCookie(true)
                        .alwaysRemember(true)
                )
                .securityContext(context -> context.requireExplicitSave(false))
                .build();
    }
}
