package dungeon.engine;

import java.io.Serializable;

/**
 * Represents an empty cell in the dungeon grid.
 * This cell has no effect on the player and operates as the default map cell.
 */
public class EmptyCell extends Cell implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Constructs an EmptyCell at the given map coordinates.
     *
     * @param x the x-coordinate position of the cell
     * @param y the y-coordinate position of the cell
     */
    public EmptyCell(int x, int y) {
        super(x, y);
    }

    /**
     * Performs action associated with this cell type.
     *
     * @param player Player interacting with this cell
     */
    @Override
    public void interact(Player player) {
        // Nothing happens
    }

    /**
     * @return The character representing the cell
     */
    @Override
    public String getIcon() {
        return "_";
    }
}
