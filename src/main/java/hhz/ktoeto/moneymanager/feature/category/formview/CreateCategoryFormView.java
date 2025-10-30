package hhz.ktoeto.moneymanager.feature.category.formview;

public class CreateCategoryFormView extends CategoryFormView {

    protected CreateCategoryFormView(CreateCategoryFormPresenter presenter) {
        super(presenter);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return false;
    }
}
