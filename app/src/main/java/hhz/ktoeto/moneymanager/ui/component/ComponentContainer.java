package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;

public class ComponentContainer extends Composite<Div> {

    private final Div header = new Div();
    private final Div content = new Div();

    public ComponentContainer() {
    }

    public ComponentContainer(String header) {
        this();
        this.setHeader(header);
    }

    public void setHeader(String text) {
        this.header.removeAll();
        this.header.add(new H2(text));
    }

    public void setHeader(Component component) {
        this.header.removeAll();
        this.header.add(component);
    }

    public void setContent(Component... components) {
        this.content.removeAll();
        this.content.add(components);
    }

    @Override
    protected Div initContent() {
        Div root = new Div();

        root.addClassName("component-container");
        root.add(this.header, this.content);

        return root;
    }
}
