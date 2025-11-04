package hhz.ktoeto.moneymanager.feature.transaction.formview;

import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.data.SimpleAllCategoriesProvider;
import hhz.ktoeto.moneymanager.feature.transaction.domain.Transaction;
import hhz.ktoeto.moneymanager.feature.transaction.domain.TransactionService;
import hhz.ktoeto.moneymanager.ui.AbstractFormViewPresenter;
import hhz.ktoeto.moneymanager.ui.component.DeleteConfirmDialog;
import hhz.ktoeto.moneymanager.ui.event.CategoryCreateRequested;
import hhz.ktoeto.moneymanager.ui.mixin.CanAddCategory;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.context.ApplicationEventPublisher;

public abstract class TransactionFormPresenter extends AbstractFormViewPresenter<Transaction> implements CanAddCategory {

    @Getter(AccessLevel.PROTECTED)
    private final transient UserContextHolder userContextHolder;
    @Getter(AccessLevel.PROTECTED)
    private final transient TransactionService transactionService;
    private final transient ApplicationEventPublisher eventPublisher;
    @Getter(AccessLevel.PROTECTED)
    private final SimpleAllCategoriesProvider categoryDataProvider;

    protected TransactionFormPresenter(UserContextHolder userContextHolder, TransactionService transactionService,
                                       ApplicationEventPublisher eventPublisher, SimpleAllCategoriesProvider categoryDataProvider) {
        this.userContextHolder = userContextHolder;
        this.transactionService = transactionService;
        this.eventPublisher = eventPublisher;
        this.categoryDataProvider = categoryDataProvider;
    }

    @Override
    public void onCategoryAdd() {
        this.eventPublisher.publishEvent(new CategoryCreateRequested(this));
    }

    @Override
    public void onDelete() {
        DeleteConfirmDialog confirmDialog = new DeleteConfirmDialog();
        confirmDialog.setHeader("Удалить транзакцию?");
        confirmDialog.addConfirmListener(event -> {
            Transaction transaction = this.getView().getEntity();
            this.transactionService.delete(transaction.getId(), this.userContextHolder.getCurrentUserId());
            confirmDialog.close();
            this.getRootDialog().close();
        });

        confirmDialog.open();
    }
}
