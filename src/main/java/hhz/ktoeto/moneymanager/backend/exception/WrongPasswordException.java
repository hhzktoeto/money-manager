package hhz.ktoeto.moneymanager.backend.exception;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
        super("Wrong password was provided");
    }
}
