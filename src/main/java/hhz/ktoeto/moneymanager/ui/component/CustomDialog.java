package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.component.field.RussianDatePicker;
import org.vaadin.addons.gl0b3.materialicons.MaterialIcons;

@Uses(TextArea.class)
@Uses(Scroller.class)
@Uses(ComboBox.class)
@Uses(MultiSelectComboBox.class)
@Uses(RussianDatePicker.class)
@Uses(BigDecimalField.class)
public class CustomDialog extends Dialog {

    private final H3 title;
    private final Button closeButton;
    private final HorizontalLayout header;
    private final FlexLayout body;

    public CustomDialog() {
        this.setCloseOnOutsideClick(false);
        this.addDialogCloseActionListener(event -> this.close());
        this.setWidthFull();
        this.addClassName(LumoUtility.MaxWidth.SCREEN_SMALL);

        header = new HorizontalLayout();
        header.addClassNames(
                LumoUtility.Margin.Bottom.XLARGE,
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.BETWEEN
        );

        title = new H3();
        header.add(title);

        closeButton = new Button(MaterialIcons.CLOSE.create());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE, ButtonVariant.LUMO_LARGE);
        closeButton.addClickListener(e -> this.close());
        header.add(closeButton);
        this.add(header);

        body = new FlexLayout();
        this.add(body);
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void addBody(Component... components) {
        body.add(components);
    }

    @Override
    public void open() {
        if (this.isOpened()) {
            this.close();
        }
        super.open();
    }

    @Override
    public void close() {
        if (this.isOpened()) {
            super.close();
        }
        body.removeAll();
    }
}
