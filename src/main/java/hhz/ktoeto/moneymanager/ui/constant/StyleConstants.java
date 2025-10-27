package hhz.ktoeto.moneymanager.ui.constant;

public final class StyleConstants {

    private StyleConstants() {}

    public static final String CLICKABLE = "clickable";
    public static final String BG_COLOR = "background-color";
    public static final String COLOR = "color";
    public static final String TRANSITION = "transition";
    public static final String TRANSFORM = "transform";
    public static final String BORDER_RADIUS = "border-radius";

    public static final class Color {

        private Color() {}

        public static final String PRIMARY_CONTRAST_40 = "var(--lumo-primary-contrast-color-40pct)";

        public static final String SUCCESS = "var(--lumo-success-color)";
        public static final String SUCCESS_10 = "var(--lumo-success-color-10pct)";

        public static final String ERROR = "var(--lumo-error-color)";
        public static final String ERROR_10 = "var(--lumo-error-color-10pct)";

        public static final String SUCCESS_CONTRAST = "var(--lumo-success-contrast-color)";
        public static final String ERROR_CONTRAST = "var(--lumo-error-contrast-color)";

        public static final String SECONDARY_TEXT = "var(--lumo-secondary-text-color)";
    }

    public static final class Translate {

        private Translate() {}

        public static final String X100 = "translateX(100%)";
        public static final String X0 = "translateX(0%)";
    }

    public static final class Transition {

        private Transition() {}

        public static final String TRANSFORM_03_EASE = "transform 0.3s ease";
        public static final String BG_COLOR_03_EASE = "background-color 0.3s ease";
    }

    public static final class Badge {

        private Badge() {}

        public static final String ERROR = "badge error";
        public static final String SUCCESS = "badge success";
    }

    public static final class BorderRadius {

        private BorderRadius() {}

        public static final String LEFT_075REM = "0.75rem 0 0 0.75rem";
        public static final String RIGHT_075REM = "0 0.75rem 0.75rem 0";
    }
}
