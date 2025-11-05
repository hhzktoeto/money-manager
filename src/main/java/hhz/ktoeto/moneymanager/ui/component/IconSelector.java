package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;

import java.util.List;

public class IconSelector extends CustomField<String> {

    private final FlexLayout dialogOpenButton;
    private final IconSelectDialog iconSelectDialog;

    private String selectedIconFileName = "default_icon.png";
    private Image selectedIconImage;

    public IconSelector() {
        this.dialogOpenButton = new FlexLayout();
        this.iconSelectDialog = new IconSelectDialog(this::setValue);
        this.selectedIconImage = new Image("categories/" + this.selectedIconFileName, "");

        this.selectedIconImage.setWidth(3, Unit.REM);

        this.dialogOpenButton.addClickListener(event -> this.iconSelectDialog.open());
        this.dialogOpenButton.addClassNames(
                StyleConstants.CLICKABLE,
                LumoUtility.BorderRadius.LARGE,
                LumoUtility.Background.CONTRAST_10,
                LumoUtility.Padding.SMALL,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.JustifyContent.CENTER
        );
        this.dialogOpenButton.add(this.selectedIconImage);

        this.add(this.dialogOpenButton);
    }

    @Override
    protected String generateModelValue() {
        return this.selectedIconFileName;
    }

    @Override
    protected void setPresentationValue(String iconFileName) {
        this.selectedIconFileName = iconFileName != null ? iconFileName : "default_icon.png";
        this.selectedIconImage.setSrc("categories/" + this.selectedIconFileName);
    }

    private static final class IconSelectDialog extends Composite<CustomDialog> {

        private final FlexLayout layout = new FlexLayout();

        private final SerializableConsumer<String> onSubmit;

        public IconSelectDialog(SerializableConsumer<String> onSubmit) {
            this.onSubmit = onSubmit;
        }

        @Override
        protected CustomDialog initContent() {
            CustomDialog root = new CustomDialog();
            this.layout.addClassNames(
                    LumoUtility.FlexWrap.WRAP,
                    LumoUtility.Gap.MEDIUM,
                    LumoUtility.Overflow.AUTO
            );
            List<String> iconFiles = List.of("default_icon.png", "travel.png");

            for (String iconFile : iconFiles) {
                FlexLayout iconContainer = new FlexLayout();
                iconContainer.addClassNames(
                        StyleConstants.CLICKABLE,
                        LumoUtility.BorderRadius.LARGE,
                        LumoUtility.Background.CONTRAST_10,
                        LumoUtility.Padding.SMALL,
                        LumoUtility.AlignItems.CENTER,
                        LumoUtility.JustifyContent.CENTER
                );

                Image icon = new Image("categories/" + iconFile, "");
                icon.setWidth(3, Unit.REM);

                iconContainer.add(icon);
                iconContainer.addClickListener(event -> {
                    onSubmit.accept(iconFile);
                    root.close();
                });

                this.layout.add(iconContainer);
            }

            root.add(this.layout);
            return root;
        }

        private void open() {
            this.getContent().open();
        }
    }
}
