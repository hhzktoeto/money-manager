package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;

public abstract class CustomCard extends Div {

    protected CustomCard() {
        addClassName("card");
    }

    public void setHeader(String header) {
        add(new H2(header));
    }
}
