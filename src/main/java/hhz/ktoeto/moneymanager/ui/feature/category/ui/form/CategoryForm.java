package hhz.ktoeto.moneymanager.ui.feature.category.ui.form;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.feature.category.domain.Category;
import hhz.ktoeto.moneymanager.ui.feature.category.ui.form.validator.CategoryNameValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryForm extends Composite<FlexLayout> {

    private final transient Runnable submitAction;
    private final transient Runnable cancelAction;

    @Getter
    private final TextField nameField;
    private final Button submitButton;
    private final Button cancelButton;

    private final Binder<Category> binder;

    public CategoryForm(Runnable submitAction, Runnable cancelAction) {
        this.submitAction = submitAction;
        this.cancelAction = cancelAction;

        this.nameField = new TextField("Имя");
        this.submitButton = new Button("Сохранить");
        this.cancelButton = new Button("Отмена");

        this.binder = new Binder<>(Category.class);
    }

    @Override
    protected FlexLayout initContent() {
        FlexLayout root = new FlexLayout();
        root.setFlexDirection(FlexLayout.FlexDirection.COLUMN);
        root.addClassNames(
                LumoUtility.MaxWidth.SCREEN_SMALL,
                LumoUtility.Padding.NONE,
                LumoUtility.Width.FULL,
                LumoUtility.AlignItems.STRETCH
        );


        nameField.setWidthFull();
        root.add(nameField);

        submitButton.addClickListener(e -> submitAction.run());
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        cancelButton.addClickListener(e -> cancelAction.run());

        HorizontalLayout buttons = new HorizontalLayout(cancelButton, submitButton);
        buttons.addClassNames(
                LumoUtility.AlignItems.STRETCH,
                LumoUtility.Margin.Top.MEDIUM,
                LumoUtility.JustifyContent.BETWEEN,
                LumoUtility.Gap.LARGE
        );
        root.add(buttons);

        binder.forField(nameField)
                .withValidator(new CategoryNameValidator())
                .bind(Category::getName, (category, name) -> category.setName(name.trim()));

        return root;
    }

    boolean writeTo(Category category) {
        return binder.writeBeanIfValid(category);
    }
}
