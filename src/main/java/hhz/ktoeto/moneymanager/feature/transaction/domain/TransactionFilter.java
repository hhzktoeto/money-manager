package hhz.ktoeto.moneymanager.feature.transaction.domain;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionFilter {

    private LocalDate fromDate;
    private LocalDate toDate;
}
