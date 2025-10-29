package hhz.ktoeto.moneymanager.ui;

@FunctionalInterface
public interface ViewPresenter<T extends View> {

    void initialize(T view);
}
