package hhz.ktoeto.moneymanager.feature.category.view;

public class CreateCategoryFormView extends CategoryFormView {

    protected CreateCategoryFormView(CreateCategoryFormPresenter presenter) {
        super(presenter);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return false;
    }
}
