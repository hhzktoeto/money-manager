package hhz.ktoeto.moneymanager.backend.transaction_domain.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }
}
