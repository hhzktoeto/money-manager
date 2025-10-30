package hhz.ktoeto.moneymanager.ui;

import com.vaadin.flow.component.Component;

import java.io.Serializable;

@FunctionalInterface
public interface View extends Serializable {

    Component asComponent();
}
