package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;

public abstract class ComponentContainer extends Div {

    protected final Div header = new Div();
    protected final Div content = new Div();

    protected ComponentContainer() {
        this.addClassName("component-container");

        this.add(this.header, this.content);
    }

    protected void setHeader(String text) {
        this.header.removeAll();
        this.header.add(new H2(text));
    }

    protected void setHeader(Component component) {
        this.header.removeAll();
        this.header.add(component);
    }

    protected void setContent(Component... components) {
        this.content.removeAll();
        this.content.add(components);
    }

    protected void addHeaderClassName(String className) {
        this.header.addClassName(className);
    }

    protected void addContentClassName(String className) {
        this.content.addClassName(className);
    }
}
