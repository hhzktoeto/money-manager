package hhz.ktoeto.moneymanager.backend.exception;

public class NonOwnerRequestException extends RuntimeException {

    public NonOwnerRequestException(String message) {
        super(message);
    }
}
