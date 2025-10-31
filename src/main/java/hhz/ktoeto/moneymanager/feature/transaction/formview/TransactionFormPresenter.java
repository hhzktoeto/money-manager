package hhz.ktoeto.moneymanager.feature.transaction.formview;

import hhz.ktoeto.moneymanager.core.security.UserContextHolder;
import hhz.ktoeto.moneymanager.feature.category.data.CategoryDataProvider;
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
    private final UserContextHolder userContextHolder;
    @Getter(AccessLevel.PROTECTED)
    private final TransactionService transactionService;
    private final ApplicationEventPublisher eventPublisher;
    @Getter(AccessLevel.PROTECTED)
    private final CategoryDataProvider categoryDataProvider;

    protected TransactionFormPresenter(UserContextHolder userContextHolder, TransactionService transactionService,
                                       ApplicationEventPublisher eventPublisher, CategoryDataProvider categoryDataProvider) {
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
