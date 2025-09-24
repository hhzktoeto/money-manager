package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.notification.Notification;

public class AddTransaction extends VerticalLayout {
    public AddTransaction() {
        FormLayout form = new FormLayout();
        NumberField amount = new NumberField("Сумма");
        DatePicker date = new DatePicker("Дата");
        TextField desc = new TextField("Описание");
        Button add = new Button("Добавить", e -> Notification.show("Добавлено", 1000, Notification.Position.BOTTOM_CENTER));
        form.add(amount, date, desc, add);
        add(form);
        addClassName("add-transaction");
    }
}
