package dungeon.engine;

import javafx.scene.layout.StackPane;

public abstract class Cell extends StackPane {
    protected int x;
    protected int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract void interact(Player player);

    public int getX() { return x; }
    public int getY() { return y; }

    public String getIcon() {
        return "?"; // default icon
    }
}
