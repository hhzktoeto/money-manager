package hhz.ktoeto.moneymanager.ui;

import hhz.ktoeto.moneymanager.ui.component.CustomDialog;

public abstract class AbstractFormViewPresenter<T> implements FormViewPresenter<T, AbstractFormView<T>> {

    protected final CustomDialog dialog = new CustomDialog();

    protected AbstractFormView<T> view;

    protected abstract String getDialogTitle();

    protected abstract AbstractFormView<T> getForm();

    @Override
    public void initialize(AbstractFormView<T> view) {
        this.view = view;
    }

    @Override
    public void openForm(T entity) {
        AbstractFormView<T> form = this.getForm();
        form.setEntity(entity);

        this.dialog.setTitle(this.getDialogTitle());
        this.dialog.addBody(form.asComponent());
        this.dialog.open();
    }

    @Override
    public void onCancel() {
        this.dialog.close();
    }
}
