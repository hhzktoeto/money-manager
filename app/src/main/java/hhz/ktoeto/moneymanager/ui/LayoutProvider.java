package hhz.ktoeto.moneymanager.ui;

import com.vaadin.flow.component.Component;

@FunctionalInterface
public interface LayoutProvider<T> {

    Component createLayout(T component);
}
