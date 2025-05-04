package dungeon.engine;

/**
 * Represents a wall in the dungeon grid.
 * This cell can limit or block players movement on the grid.
 */
public class WallCell extends Cell {
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
