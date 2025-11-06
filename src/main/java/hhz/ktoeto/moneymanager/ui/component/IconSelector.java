package hhz.ktoeto.moneymanager.ui.component;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hhz.ktoeto.moneymanager.ui.constant.StyleConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class IconSelector extends CustomField<String> {

    private static final String DEFAULT_ICON_NAME = "default_icon.png";

    private final FlexLayout dialogOpenButton;
    private final IconSelectDialog iconSelectDialog;
    private final String iconFilePathPrefix;
    private final Image selectedIconImage;

    private String selectedIconFileName = DEFAULT_ICON_NAME;

    public IconSelector(String iconFilePathPrefix) {
        this.iconFilePathPrefix = iconFilePathPrefix;
        this.dialogOpenButton = new FlexLayout();
        this.iconSelectDialog = new IconSelectDialog(this::setValue, this.iconFilePathPrefix);
        this.selectedIconImage = new Image(this.iconFilePathPrefix + this.selectedIconFileName, "");

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
        this.selectedIconFileName = iconFileName != null ? iconFileName : DEFAULT_ICON_NAME;
        this.selectedIconImage.setSrc(this.iconFilePathPrefix + this.selectedIconFileName);
    }

    @Slf4j
    private static final class IconSelectDialog extends Composite<CustomDialog> {

        private final FlexLayout layout = new FlexLayout();

        private final SerializableConsumer<String> onSubmit;
        private final String iconFilePathPrefix;

        public IconSelectDialog(SerializableConsumer<String> onSubmit, String iconFilePathPrefix) {
            this.onSubmit = onSubmit;
            this.iconFilePathPrefix = iconFilePathPrefix;
        }

        @Override
        protected CustomDialog initContent() {
            CustomDialog root = new CustomDialog();
            this.layout.addClassNames(
                    LumoUtility.FlexWrap.WRAP,
                    LumoUtility.Gap.MEDIUM,
                    LumoUtility.Overflow.AUTO
            );
            List<String> iconFiles;
            try {
                ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                Resource[] resources = resolver.getResources("classpath:/META-INF/resources/" + this.iconFilePathPrefix + "*.png");
                iconFiles = Arrays.stream(resources)
                        .map(Resource::getFilename)
                        .filter(Objects::nonNull)
                        .filter(name -> !Objects.equals(DEFAULT_ICON_NAME, name))
                        .sorted()
                        .toList();
            } catch (IOException e) {
                log.error("Failed to load icons in IconSelectDialog", e);
                throw new UncheckedIOException(e);
            }

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

                Image icon = new Image(this.iconFilePathPrefix + iconFile, "");
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
