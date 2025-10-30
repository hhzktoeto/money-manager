package hhz.ktoeto.moneymanager.feature.category.view;

public class EditCategoryFormView extends CategoryFormView {

    protected EditCategoryFormView(EditCategoryFormPresenter presenter) {
        super(presenter);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return true;
    }
}
