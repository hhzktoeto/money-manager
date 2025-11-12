package hhz.ktoeto.moneymanager.ui.constant;

public final class StyleConstants {

    private StyleConstants() {}

    public static final String CLICKABLE = "clickable";
    public static final String BG_COLOR = "background-color";
    public static final String COLOR = "color";
    public static final String TRANSITION = "transition";
    public static final String TRANSFORM = "transform";
    public static final String BORDER_RADIUS = "border-radius";

    public static final com.storedobject.chart.Color[] CHARTS_COLORS = {
            new com.storedobject.chart.Color("#A44E58"), new com.storedobject.chart.Color("#6B8BAB"),
            new com.storedobject.chart.Color("#EBCB8B"), new com.storedobject.chart.Color("#8FBCBB"),
            new com.storedobject.chart.Color("#486B95"), new com.storedobject.chart.Color("#5E81AC"),
            new com.storedobject.chart.Color("#B48EAD"), new com.storedobject.chart.Color("#D08770"),
            new com.storedobject.chart.Color("#88C0D0"), new com.storedobject.chart.Color("#81A1C1"),
            new com.storedobject.chart.Color("#C57B85"), new com.storedobject.chart.Color("#8EAA76"),
            new com.storedobject.chart.Color("#BF616A"), new com.storedobject.chart.Color("#9E7897"),
            new com.storedobject.chart.Color("#D5B575"), new com.storedobject.chart.Color("#A3BE8C"),
            new com.storedobject.chart.Color("#79A6A5"), new com.storedobject.chart.Color("#DE9260"),
            new com.storedobject.chart.Color("#BA7160"), new com.storedobject.chart.Color("#A94B54"),
            new com.storedobject.chart.Color("#72AAB9"), new com.storedobject.chart.Color("#6A9AB0"),
            new com.storedobject.chart.Color("#8DA876")
    };

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
