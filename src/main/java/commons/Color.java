package commons;

public enum Color {
    WHITE ("white"),
    BLACK ("black"),
    BLUE ("blue"),
    RED ("red"),
    GREEN ("green"),
    GRAY ("gray"),
    YELLOW ("yellow"),
    BROWN ("brown"),
    ORANGE ("orange"),
    DARK_BLUE ("dark blue"),
    DARK_GREEN ("dark green");

    public final String name;

    Color(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
