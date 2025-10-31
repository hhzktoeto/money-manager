package hhz.ktoeto.moneymanager.ui;

import hhz.ktoeto.moneymanager.ui.component.CustomDialog;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractFormViewPresenter<T> implements FormViewPresenter<T> {

    @Getter(AccessLevel.PROTECTED)
    private final CustomDialog rootDialog = new CustomDialog();
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PROTECTED)
    private AbstractFormView<T> view;

    protected abstract String getDialogTitle();

    @Override
    @PostConstruct
    public abstract void initialize();

    @Override
    public void openForm(T entity) {
        this.view.setEntity(entity);

        this.rootDialog.setTitle(this.getDialogTitle());
        this.rootDialog.addBody(this.view.asComponent());
        this.rootDialog.open();
    }

    @Override
    public void onCancel() {
        this.rootDialog.close();
    }

    protected Logger log() {
        return this.log;
    }
}
