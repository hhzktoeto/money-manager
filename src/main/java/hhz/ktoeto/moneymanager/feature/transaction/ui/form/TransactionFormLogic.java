package hhz.ktoeto.moneymanager.feature.transaction.ui.form;

public interface TransactionFormLogic {

    void onSubmit(TransactionForm form);

    void onCancel(TransactionForm form);

    void onCategoryAdd(TransactionForm form);
}
