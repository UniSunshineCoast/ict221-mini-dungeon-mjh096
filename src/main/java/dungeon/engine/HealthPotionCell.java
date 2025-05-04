package dungeon.engine;

/**
 * Represents an Health Potion in the dungeon grid.
 * This cell increases the health of the player after stepping on it.
 */
public class HealthPotionCell extends Cell {
    /**
     * Constructs a HealthPotionCell at the given map coordinates.
     *
     * @param x the x-coordinate position of the cell
     * @param y the y-coordinate position of the cell
     */
    public HealthPotionCell(int x, int y) {
        super(x, y);
    }

    /**
     * Performs action associated with this cell type.
     *
     * @param player Player interacting with this cell
     */
    @Override
    public void interact(Player player) {
        player.updateHP(4); // cap at 10 in implementation
        // Potion disappears after use
    }

    /**
     * @return The character representing the cell
     */
    @Override
    public String getIcon() {
        return "H";
    }
}
