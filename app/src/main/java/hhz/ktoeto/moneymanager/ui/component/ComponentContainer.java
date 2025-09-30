package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;

public abstract class ComponentContainer extends Div {

    protected final Div header = new Div();
    protected final Div content = new Div();

    protected ComponentContainer() {
        addClassName("container");
        this.header.setClassName("container-header");

        add(this.header, this.content);
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

    public void addContentClassName(String className) {
        this.content.addClassName(className);
    }
}
