package hhz.ktoeto.moneymanager.transaction.model.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionDTO(
        String type,
        String category,
        LocalDate date,
        BigDecimal amount,
        String description
) {
}
