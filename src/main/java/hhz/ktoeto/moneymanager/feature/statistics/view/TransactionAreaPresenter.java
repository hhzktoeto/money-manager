package hhz.ktoeto.moneymanager.feature.statistics.view;

import com.vaadin.flow.data.provider.DataChangeEvent;
import com.vaadin.flow.data.provider.DataProviderListener;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import hhz.ktoeto.moneymanager.feature.statistics.data.StatisticsDataProvider;
import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.TransactionSum;
import hhz.ktoeto.moneymanager.ui.ViewPresenter;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@UIScope
@SpringComponent
@RequiredArgsConstructor
public class TransactionAreaPresenter implements ViewPresenter, DataProviderListener<Object> {

    private final StatisticsDataProvider dataProvider;

    @Getter
    private TransactionAreaView view;

    @Override
    public void onDataChange(DataChangeEvent<Object> event) {
        List<TransactionSum> transactionSums = this.dataProvider.getTransactionSums();

        this.view.update(transactionSums);
    }

    @Override
    @PostConstruct
    public void initialize() {
        this.view = new TransactionAreaView(this);

        this.dataProvider.addDataProviderListener(this);
        // Call on init to make charts visible without updating
        this.onDataChange(null);
    }
}
