package sdv.devduo.yukool.model;

/** Enum de nutrition*/
public enum NutritionGradeFr {
    A("a"),
    B("b"),
    C("c"),
    D("d"),
    E("e"),
    F("f");

    /** La valeur de la nutrition*/
    private final String value;

    NutritionGradeFr(String value) {
        this.value = value;
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
