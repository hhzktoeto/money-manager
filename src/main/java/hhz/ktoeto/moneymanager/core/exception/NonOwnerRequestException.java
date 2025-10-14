package hhz.ktoeto.moneymanager.core.exception;

public class NonOwnerRequestException extends RuntimeException {

    public NonOwnerRequestException(String message) {
        super(message);
    }
}
