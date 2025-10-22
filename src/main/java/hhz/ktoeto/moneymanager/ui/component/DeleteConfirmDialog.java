package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;

public class DeleteConfirmDialog extends ConfirmDialog {

    public DeleteConfirmDialog() {
        this.setRejectable(true);
        this.setRejectText("Ой, нет");
        this.setRejectButtonTheme(ButtonVariant.LUMO_CONTRAST.getVariantName());

        this.setConfirmText("Да");
    }
}
