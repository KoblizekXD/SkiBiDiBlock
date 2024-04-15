package lol.koblizek.biblock.model.loader;

public enum Def {
    V,
    VT,
    VN,
    F,
    O,
    G,
    S,
    L,
    MTL,
    USEMTL,
    UNKNOWN;

    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public static Def from(String def) {
        try {
            return valueOf(def.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }
}
