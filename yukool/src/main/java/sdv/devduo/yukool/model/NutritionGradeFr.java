package sdv.devduo.yukool.model;

public enum NutritionGradeFr {
    A("a"),
    B("b"),
    C("c"),
    D("d"),
    E("e"),
    F("f");

    private final String value;

    NutritionGradeFr(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static NutritionGradeFr fromString(String grade) {
        for (NutritionGradeFr ng : NutritionGradeFr.values()) {
            if (ng.value.equalsIgnoreCase(grade)) {
                return ng;
            }
        }
        throw new IllegalArgumentException("Unknown nutrition grade: " + grade);
    }
}
