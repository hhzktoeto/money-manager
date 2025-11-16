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

    private Component visibleComponent;

    @Override
    protected FlexLayout initContent() {
        FlexLayout root = new FlexLayout();
        root.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.AlignContent.CENTER,
                LumoUtility.JustifyContent.CENTER
        );

        return root;
    }

    @Override
    public Component asComponent() {
        return this;
    }

    @Override
    public void update(List<TransactionSum> data) {
        FlexLayout root = this.getContent();
        if (this.visibleComponent != null) {
            root.remove(this.visibleComponent);
        }
        if (data.isEmpty()) {
            EmptyDataImage image = new EmptyDataImage();
            image.setText("Нет транзакций для отображения статистики");
            image.setImageMaxWidth(13, Unit.REM);

            this.visibleComponent = image;
        } else {
            this.visibleComponent = new TransactionSumArea(data);
        }

        root.add(this.visibleComponent);
    }
}
