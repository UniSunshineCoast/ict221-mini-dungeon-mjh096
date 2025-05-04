package dungeon.engine;

/**
 * Represents a Melee Mutant in the dungeon grid.
 * Stepping on this cell reduces players HP and increases score.
 */
public class MeleeMutantCell extends Cell {
    /**
     * Constructs a MeleeMutantCell at the given map coordinates.
     *
     * @param x the x-coordinate position of the cell
     * @param y the y-coordinate position of the cell
     */
    public MeleeMutantCell(int x, int y) {
        super(x, y);
    }

    /**
     * Performs action associated with this cell type.
     *
     * @param player Player interacting with this cell
     */
    @Override
    public void interact(Player player) {
        player.updateHP(-2);
        player.updateScore(2);
        // Mutant removed after interaction
    }

    /**
     * @return The character representing the cell
     */
    @Override
    public String getIcon() {
        return "M";
    }
}
