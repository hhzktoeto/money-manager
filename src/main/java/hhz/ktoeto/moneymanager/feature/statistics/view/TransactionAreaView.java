package hhz.ktoeto.moneymanager.feature.statistics.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.statistics.domain.dto.TransactionSum;
import hhz.ktoeto.moneymanager.ui.View;
import hhz.ktoeto.moneymanager.ui.component.EmptyDataImage;
import hhz.ktoeto.moneymanager.ui.component.chart.TransactionSumArea;
import hhz.ktoeto.moneymanager.ui.mixin.HasUpdatableData;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TransactionAreaView extends Composite<FlexLayout> implements View, HasUpdatableData<List<TransactionSum>> {

    private final TransactionAreaPresenter presenter;

    private final EmptyDataImage emptyDataImage;

    private TransactionSumArea transactionSumArea;

    public TransactionAreaView(TransactionAreaPresenter presenter) {
        this.presenter = presenter;

        this.emptyDataImage = new EmptyDataImage();
        this.emptyDataImage.setText("Нет транзакций для отображения статистики");
        this.emptyDataImage.setImageMaxWidth(16, Unit.REM);
    }

    @Override
    protected FlexLayout initContent() {
        FlexLayout root = new FlexLayout();
        root.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignContent.CENTER,
                LumoUtility.JustifyContent.CENTER
        );

        root.add(this.emptyDataImage);

        return root;
    }

    @Override
    public Component asComponent() {
        return this;
    }

    @Override
    public void update(List<TransactionSum> data) {
        FlexLayout root = this.getContent();
        boolean isChartInRoot = root.getChildren()
                .anyMatch(child -> child.equals(this.transactionSumArea));

        if (data.isEmpty()) {
            if (isChartInRoot) {
                root.remove(this.transactionSumArea);
            }
            this.emptyDataImage.setVisible(true);
            return;
        }

        if (this.transactionSumArea == null) {
            this.transactionSumArea = new TransactionSumArea();
        }
        this.transactionSumArea.updateData(data);
        if (!isChartInRoot) {
            root.add(this.transactionSumArea);
        }
        this.emptyDataImage.setVisible(false);
        this.transactionSumArea.setVisible(true);
    }
}
