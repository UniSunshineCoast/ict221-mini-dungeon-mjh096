package dungeon.engine;

import java.io.Serializable;

/**
 * Represents a wall in the dungeon grid.
 * This cell can limit or block players movement on the grid.
 */
public class WallCell extends Cell implements Serializable {
    /**
     * Unique ID for serialization compatibility.
     * Used to ensure that object matches the version of the class.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Constructs a WallCell at the given map coordinates.
     *
     * @param x the x-coordinate position of the cell
     * @param y the y-coordinate position of the cell
     */
    public WallCell(int x, int y) {
        super(x, y);
    }

    /**
     * Performs action associated with this cell type.
     *
     * @param player Player interacting with this cell
     */
    @Override
    public void interact(Player player) {
        // Block movement (possibly revert)
    }

    /**
     * @return The character representing the cell
     */
    @Override
    public String getIcon() {
        return "#";
    }
}
