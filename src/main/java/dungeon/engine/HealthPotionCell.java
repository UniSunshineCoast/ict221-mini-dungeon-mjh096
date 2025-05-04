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
        player.healPlayer(4);
        System.out.println("You found a Health Potion! +4 HP");

        player.getGameEngine().clearCell(x, y); // Sets Cell as an EmptyCell
    }

    /**
     * @return The character representing the cell
     */
    @Override
    public String getIcon() {
        return "H";
    }
}
