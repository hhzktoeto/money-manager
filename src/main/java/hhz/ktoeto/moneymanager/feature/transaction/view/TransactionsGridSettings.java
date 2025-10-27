package hhz.ktoeto.moneymanager.feature.transaction.view;

import com.vaadin.componentfactory.DateRange;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridSettingsView;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridSettingsViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.TransactionsGridViewPresenter;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionFilter;
import hhz.ktoeto.moneymanager.ui.component.RussianDateRangePicker;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Set;
import java.util.stream.Collectors;

@UIScope
@SpringComponent
public class TransactionsGridSettings extends Composite<Details> implements TransactionsGridSettingsView {

    private final TransactionsGridViewPresenter gridPresenter;
    private final TransactionsGridSettingsViewPresenter settingsPresenter;

    private final MultiSelectComboBox<Category> categoryMultiSelect;
    private final RussianDateRangePicker dateRangePicker;

    public TransactionsGridSettings(@Qualifier("allTransactionsPresenter") TransactionsGridViewPresenter gridPresenter,
                                    TransactionsGridSettingsViewPresenter settingsPresenter) {
        this.gridPresenter = gridPresenter;
        this.settingsPresenter = settingsPresenter;
        this.settingsPresenter.setView(this);
        this.categoryMultiSelect = new MultiSelectComboBox<>("Категории");
        this.dateRangePicker = new RussianDateRangePicker("Период");
    }

    @Override
    protected Details initContent() {
        Details root = new Details("Настройки");

        Div contentWrapper = new Div();

        this.configureContentWrapper(contentWrapper);

        root.add(contentWrapper);

        return root;
    }

    @Override
    public Component asComponent() {
        return this;
    }

    private void configureContentWrapper(Div wrapper) {
        this.categoryMultiSelect.setItemLabelGenerator(Category::getName);
        this.categoryMultiSelect.setClearButtonVisible(true);
        this.categoryMultiSelect.addValueChangeListener(event -> {
            TransactionFilter filter = this.gridPresenter.getFilter();
            Set<Long> selectedCategoriesIds = event.getValue().stream()
                    .map(Category::getId)
                    .collect(Collectors.toSet());
            filter.setCategoriesIds(selectedCategoriesIds);
            this.gridPresenter.setFilter(filter);
        });
        this.categoryMultiSelect.setItems(this.settingsPresenter.getCategoriesProvider());

        TransactionFilter transactionFilter = this.gridPresenter.getFilter();
        this.dateRangePicker.setValue(new DateRange(transactionFilter.getFromDate(), transactionFilter.getToDate()));
        this.dateRangePicker.addValueChangeListener(event -> {
            TransactionFilter filter = this.gridPresenter.getFilter();
            DateRange selectedRange = this.dateRangePicker.getValue();
            filter.setFromDate(selectedRange.getStartDate());
            filter.setToDate(selectedRange.getEndDate());
            this.gridPresenter.setFilter(filter);
            this.dateRangePicker.suppressKeyboard();
        });

        wrapper.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Gap.Column.SMALL,
                LumoUtility.Display.GRID,
                LumoUtility.Grid.FLOW_ROW,
                LumoUtility.Grid.Column.COLUMNS_1,
                LumoUtility.Grid.Breakpoint.Small.COLUMNS_2
        );
        wrapper.add(this.categoryMultiSelect, this.dateRangePicker);
    }
}
