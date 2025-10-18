package hhz.ktoeto.moneymanager.ui.feature.user.domain;

import lombok.Data;

@Data
public class RegisterRequest {

    private String login;
    private String password;
    private String email;
    private String phone;
}
