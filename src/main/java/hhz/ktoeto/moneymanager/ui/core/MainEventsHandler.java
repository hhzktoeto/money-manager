package hhz.ktoeto.moneymanager.ui.core;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import hhz.ktoeto.moneymanager.core.event.*;
import org.springframework.context.event.EventListener;
import org.vaadin.addons.gl0b3.materialicons.MaterialIcons;

@SpringComponent
@VaadinSessionScope
public class MainEventsHandler {

    private final MaterialIcons successIcon = MaterialIcons.CHECK_CIRCLE;
    private final MaterialIcons warningIcon = MaterialIcons.WARNING;
    private final MaterialIcons errorIcon = MaterialIcons.ERROR;

    private final String categoryEntityName = "категории";
    private final String transactionEntityName = "транзакции";
    private final String budgetEntityName = "бюджета";

    @EventListener(CategoryCreatedEvent.class)
    private void onCategoryCreated() {
        this.onEntityCreation(categoryEntityName);
    }

    @EventListener(CategoryUpdatedEvent.class)
    private void onCategoryUpdated() {
        this.onEntityUpdate(categoryEntityName);
    }

    @EventListener(CategoryDeletedEvent.class)
    private void onCategoryDeleted() {
        this.onEntityDelete(categoryEntityName);
    }

    @EventListener(TransactionCreatedEvent.class)
    private void onTransactionCreated() {
        this.onEntityCreation(transactionEntityName);
    }

    @EventListener(TransactionUpdatedEvent.class)
    private void onTransactionUpdated() {
        this.onEntityUpdate(transactionEntityName);
    }

    @EventListener(TransactionDeletedEvent.class)
    private void onTransactionDeleted() {
        this.onEntityDelete(transactionEntityName);
    }

    @EventListener(BudgetCreatedEvent.class)
    private void onBudgetCreated() {
        this.onEntityCreation(budgetEntityName);
    }

    @EventListener(BudgetUpdatedEvent.class)
    private void onBudgetUpdated() {
        this.onEntityUpdate(budgetEntityName);
    }

    @EventListener(BudgetDeletedEvent.class)
    private void onBudgetDeleted() {
        this.onEntityDelete(budgetEntityName);
    }

    private void onEntityCreation(String entityName) {
        this.showNotification("Создание " + entityName + " прошло успешно", NotificationVariant.LUMO_SUCCESS);
    }

    private void onEntityUpdate(String entityName) {
        this.showNotification("Обновление " + entityName + " прошло успешно", NotificationVariant.LUMO_SUCCESS);
    }

    private void onEntityDelete(String entityName) {
        this.showNotification("Удаление " + entityName + " прошло успешно", NotificationVariant.LUMO_SUCCESS);
    }

    private void showNotification(String text, NotificationVariant variant) {
        Notification notification = new Notification();
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.setDuration(variant == NotificationVariant.LUMO_SUCCESS ? 2500 : 5000);
        notification.addThemeVariants(variant);

        Icon icon = switch (variant) {
            case LUMO_SUCCESS -> successIcon.create();
            case LUMO_WARNING -> warningIcon.create();
            case LUMO_ERROR -> errorIcon.create();
            default -> null;
        };
        Button closeButton = new Button(MaterialIcons.CLOSE.create());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.addClickListener(e -> notification.close());

        HorizontalLayout notificationLayout = new HorizontalLayout();
        notificationLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        if (icon != null) {
            notificationLayout.add(icon);
        }
        notificationLayout.add(new Text(text), closeButton);

        notification.add(notificationLayout);
        notification.open();
    }
}
