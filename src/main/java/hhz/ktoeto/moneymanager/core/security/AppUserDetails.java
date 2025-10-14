package hhz.ktoeto.moneymanager.core.security;

import hhz.ktoeto.moneymanager.feature.user.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class AppUserDetails implements UserDetails {

    private final Long id;
    private final String username;
    private final String password;

    public AppUserDetails(User userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getLogin();
        this.password = userEntity.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }
}
