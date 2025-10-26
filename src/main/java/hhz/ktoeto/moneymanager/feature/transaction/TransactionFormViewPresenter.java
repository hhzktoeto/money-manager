package hhz.ktoeto.moneymanager.feature.transaction;

import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.ui.CanAddCategory;
import hhz.ktoeto.moneymanager.ui.FormViewPresenter;

public interface TransactionFormViewPresenter extends FormViewPresenter<Transaction, TransactionFormView>, CanAddCategory {
}
