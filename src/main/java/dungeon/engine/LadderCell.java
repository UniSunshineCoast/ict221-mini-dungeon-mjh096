package dungeon.engine;

/**
 * Represents a ladder cell in the dungeon grid.
 * This cell advances player to the next level or exits the game .
 */
public class LadderCell extends Cell {
    /**
     * Constructs a LadderCell at the given map coordinates.
     *
     * @param x the x-coordinate position of the cell
     * @param y the y-coordinate position of the cell
     */
    public LadderCell(int x, int y) {
        super(x, y);
    }

    /**
     * Performs action associated with this cell type.
     *
     * @param player Player interacting with this cell
     */
    @Override
    public void interact(Player player) {
        System.out.println("You found a ladder!");
        // Advance to next level or end game
    }

    /**
     * @return The character representing the cell
     */
    @Override
    public String getIcon() {
        return "L";
    }
}
