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

    private static final MaterialIcons SUCCESS_ICON = MaterialIcons.CHECK_CIRCLE;
    private static final MaterialIcons WARNING_ICON = MaterialIcons.WARNING;
    private static final MaterialIcons ERROR_ICON = MaterialIcons.ERROR;

    private static final String SUCCESS = "прошло успешно";
    private static final String CATEGORY_ENTITY_NAME = "категории";
    private static final String TRANSACTION_ENTITY_NAME = "транзакции";
    private static final String BUDGET_ENTITY_NAME = "бюджета";

    @EventListener(CategoryCreatedEvent.class)
    private void onCategoryCreated() {
        this.onEntityCreation(CATEGORY_ENTITY_NAME);
    }

    @EventListener(CategoryUpdatedEvent.class)
    private void onCategoryUpdated() {
        this.onEntityUpdate(CATEGORY_ENTITY_NAME);
    }

    @EventListener(CategoryDeletedEvent.class)
    private void onCategoryDeleted() {
        this.onEntityDelete(CATEGORY_ENTITY_NAME);
    }

    @EventListener(TransactionCreatedEvent.class)
    private void onTransactionCreated() {
        this.onEntityCreation(TRANSACTION_ENTITY_NAME);
    }

    @EventListener(TransactionUpdatedEvent.class)
    private void onTransactionUpdated() {
        this.onEntityUpdate(TRANSACTION_ENTITY_NAME);
    }

    @EventListener(TransactionDeletedEvent.class)
    private void onTransactionDeleted() {
        this.onEntityDelete(TRANSACTION_ENTITY_NAME);
    }

    @EventListener(BudgetCreatedEvent.class)
    private void onBudgetCreated() {
        this.onEntityCreation(BUDGET_ENTITY_NAME);
    }

    @EventListener(BudgetUpdatedEvent.class)
    private void onBudgetUpdated() {
        this.onEntityUpdate(BUDGET_ENTITY_NAME);
    }

    @EventListener(BudgetDeletedEvent.class)
    private void onBudgetDeleted() {
        this.onEntityDelete(BUDGET_ENTITY_NAME);
    }

    private void onEntityCreation(String entityName) {
        this.showNotification("Создание %s %s".formatted(entityName, SUCCESS), NotificationVariant.LUMO_SUCCESS);
    }

    private void onEntityUpdate(String entityName) {
        this.showNotification("Обновление %s %s".formatted(entityName, SUCCESS), NotificationVariant.LUMO_SUCCESS);
    }

    private void onEntityDelete(String entityName) {
        this.showNotification("Удаление %s %s".formatted(entityName, SUCCESS), NotificationVariant.LUMO_SUCCESS);
    }

    private void showNotification(String text, NotificationVariant variant) {
        Notification notification = new Notification();
        notification.setPosition(Notification.Position.TOP_CENTER);
        notification.setDuration(variant == NotificationVariant.LUMO_SUCCESS ? 1750 : 5000);
        notification.addThemeVariants(variant);

        Icon icon = switch (variant) {
            case LUMO_SUCCESS -> SUCCESS_ICON.create();
            case LUMO_WARNING -> WARNING_ICON.create();
            case LUMO_ERROR -> ERROR_ICON.create();
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
