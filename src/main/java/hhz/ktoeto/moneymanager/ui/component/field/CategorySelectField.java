package hhz.ktoeto.moneymanager.ui.component.field;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.data.provider.ListDataProvider;
import hhz.ktoeto.moneymanager.feature.category.domain.Category;
import org.vaadin.addons.gl0b3.materialicons.MaterialIcons;

public class CategorySelectField extends AbstractFieldWithAction<Category, ComboBox<Category>> {

    public CategorySelectField() {
        super(new ComboBox<>("Категория"), MaterialIcons.ADD);

        this.getField().setItemLabelGenerator(Category::getName);

        this.getActionButton().setHeight(this.getField().getHeight());
        this.getActionButton().setTooltipText("Добавить категорию");
    }

    public void setItems(ListDataProvider<Category> dataProvider) {
        this.getField().setItems(dataProvider);
    }
}
