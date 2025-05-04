package dungeon.engine;

import javafx.scene.layout.StackPane;

import java.io.Serializable;

/**
 * Abstract class representing a cell in the dungeon grid.
 * Each cell occupies a specific location and may have unique interactions
 * when the player moves onto it.
 *
 * Subclasses must implement the {@link #interact(Player)} method to define
 * their specific behavior (e.g., damaging the player, awarding gold, etc.).
 */
public abstract class Cell extends StackPane implements Serializable {

    private static final long serialVersionUID = 1L;
    protected int x;
    protected int y;

    /**
     * Constructs a Cell at the given map coordinates.
     *
     * @param x the x-coordinate position of the cell
     * @param y the y-coordinate position of the cell
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Performs action associated with this cell type.
     *
     * @param player Player interacting with this cell
     */
    public abstract void interact(Player player);

    /**
     * @return returns the x-coordinate position of the cell.
     */
    public int getX() { return x; }

    /**
     * @return returns the y-coordinate position of the cell.
     */
    public int getY() { return y; }

    /**
     * @return The character representing the cell
     */
    public String getIcon() {
        return "?"; // default icon
    }
}
