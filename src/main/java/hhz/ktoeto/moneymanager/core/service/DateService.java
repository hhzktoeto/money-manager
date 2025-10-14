package hhz.ktoeto.moneymanager.core.service;

import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service
public class DateService {

    private final Clock clock = Clock.systemDefaultZone();

    public LocalDate currentMonthStart() {
        return LocalDate.now(clock).with(TemporalAdjusters.firstDayOfMonth());
    }

    public LocalDate currentMonthEnd() {
        return LocalDate.now(clock).with(TemporalAdjusters.lastDayOfMonth());
    }
}
