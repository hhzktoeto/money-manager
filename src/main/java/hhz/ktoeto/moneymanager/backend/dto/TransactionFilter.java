package hhz.ktoeto.moneymanager.backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionFilter {

    private LocalDate fromDate;
    private LocalDate toDate;
}
