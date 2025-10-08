package hhz.ktoeto.moneymanager.ui.transaction.form;

public interface TransactionFormLogic {

    void onSubmit(TransactionForm form);

    void onCancel(TransactionForm form);

    void onCategoryAdd(TransactionForm form);
}
