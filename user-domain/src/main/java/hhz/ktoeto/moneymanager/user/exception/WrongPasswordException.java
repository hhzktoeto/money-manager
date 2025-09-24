package hhz.ktoeto.moneymanager.user.exception;

public class WrongPasswordException extends RuntimeException {

    public WrongPasswordException() {
        super("Wrong password was provided");
    }
}
