package hhz.ktoeto.moneymanager.transaction.exception;

public class NonOwnerRequestException extends RuntimeException {

    public NonOwnerRequestException(String message) {
        super(message);
    }
}
