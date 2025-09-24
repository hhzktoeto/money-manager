package hhz.ktoeto.moneymanager.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "No login was provided for user's registration")
        @Size(min = 3, max = 64, message = "User's login length must be between 3 and 64 characters")
        String login,
        @NotBlank(message = "No password was provided for user's registration")
        @Size(min = 8, max = 128, message = "User's password length must be between 8 and 128 characters")
        String password,
        @Email
        String email,
        String phone
) {
}
