package hhz.ktoeto.moneymanager.backend.transaction_domain.exception;

public class NonOwnerRequestException extends RuntimeException {

    public NonOwnerRequestException(String message) {
        super(message);
    }
}
