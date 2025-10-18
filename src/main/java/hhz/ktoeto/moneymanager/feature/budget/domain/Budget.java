package hhz.ktoeto.moneymanager.feature.budget.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
}
