package hhz.ktoeto.moneymanager.utils;

public final class StylingUtils {

    private StylingUtils() {
    }

    public static final String CLICKABLE = "clickable";
    public static final String LEFT = "left";
    public static final String BG_COLOR = "background-color";
    public static final String COLOR = "color";

    public static final String TRANSITION = "transition";

    public static final class Color {
        public static final String SUCCESS = "var(--lumo-success-color)";
        public static final String SUCCESS_10 = "var(--lumo-success-color-10pct)" ;

        public static final String ERROR = "var(--lumo-error-color)";
        public static final String ERROR_10 = "var(--lumo-error-color-10pct)" ;

        public static final String SUCCESS_CONTRAST = "var(--lumo-success-contrast-color)";
        public static final String ERROR_CONTRAST = "var(--lumo-error-contrast-color)";

        public static final String SECONDARY_TEXT = "var(--lumo-secondary-text-color)";
    }

    public static final class Transition {
        public static final String LEFT_03_EASE = "left 0.3s ease";
        public static final String BG_COLOR_03_EASE = "background-color 0.3s ease";
    }
}
