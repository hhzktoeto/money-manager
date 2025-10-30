package hhz.ktoeto.moneymanager.ui;

import hhz.ktoeto.moneymanager.ui.component.CustomDialog;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractFormViewPresenter<T> implements FormViewPresenter<T> {

    protected final CustomDialog dialog = new CustomDialog();
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected AbstractFormView<T> view;

    protected abstract String getDialogTitle();

    @Override
    @PostConstruct
    public abstract void initialize();

    @Override
    public void openForm(T entity) {
        this.view.setEntity(entity);

        this.dialog.setTitle(this.getDialogTitle());
        this.dialog.addBody(this.view.asComponent());
        this.dialog.open();
    }

    @Override
    public void onCancel() {
        this.dialog.close();
    }
}
