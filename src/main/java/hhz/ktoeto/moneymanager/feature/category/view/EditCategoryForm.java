package hhz.ktoeto.moneymanager.feature.category.view;

import hhz.ktoeto.moneymanager.feature.category.CategoryFormViewPresenter;

public class EditCategoryForm extends AbstractCategoryFormView {

    protected EditCategoryForm(CategoryFormViewPresenter presenter) {
        super(presenter);
    }

    @Override
    protected boolean isDeleteButtonVisible() {
        return true;
    }
}
