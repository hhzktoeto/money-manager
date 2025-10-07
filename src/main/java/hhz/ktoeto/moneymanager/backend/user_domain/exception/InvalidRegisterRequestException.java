package hhz.ktoeto.moneymanager.backend.user_domain.exception;

public class InvalidRegisterRequestException extends RuntimeException {

    public InvalidRegisterRequestException(String message) {
        super(message);
    }
}
