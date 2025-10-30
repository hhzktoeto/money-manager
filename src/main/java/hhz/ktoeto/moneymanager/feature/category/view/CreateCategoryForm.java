package hhz.ktoeto.moneymanager.feature.category.view;

import hhz.ktoeto.moneymanager.feature.category.CategoryFormViewPresenter;

public class CreateCategoryForm extends AbstractCategoryFormView {

    protected CreateCategoryForm(CategoryFormViewPresenter presenter) {
        super(presenter);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return false;
    }
}
