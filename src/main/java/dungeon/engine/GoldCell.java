package dungeon.engine;

/**
 * Represents a Gold Coin in the dungeon grid.
 * This cell will increase the players score after stepping on it.
 */
public class GoldCell extends Cell {
    /**
     * Constructs an EmptyCell at the given map coordinates.
     *
     * @param x the x-coordinate position of the cell
     * @param y the y-coordinate position of the cell
     */
    public GoldCell(int x, int y) {
        super(x, y);
    }

    /**
     * Performs action associated with this cell type.
     * Updates Players Score to reflect the amount of Gold received and prompts message to user.
     *
     * @param player Player interacting with this cell
     */
    @Override
    public void interact(Player player) {
        player.updateScore(2);
        System.out.println("You found gold! +2 score");

        player.getGameEngine().clearCell(x, y); // Sets Cell as an EmptyCell
    }

    /**
     * @return The character representing the cell
     */
    @Override
    public String getIcon() {
        return "G";
    }
}
