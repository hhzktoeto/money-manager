package hhz.ktoeto.moneymanager.backend.user_domain.exception;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
        super("Wrong password was provided");
    }
}
