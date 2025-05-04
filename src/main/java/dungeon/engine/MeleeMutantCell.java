package dungeon.engine;

import java.io.Serializable;

/**
 * Represents a Melee Mutant in the dungeon grid.
 * Stepping on this cell reduces players HP and increases score.
 */
public class MeleeMutantCell extends Cell implements Serializable {
    /**
     * Unique ID for serialization compatibility.
     * Used to ensure that object matches the version of the class.
     */
    private static final long serialVersionUID = 1L;
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
     * Player fights with Mutant, loses health and gains points
     *
     * @param player Player interacting with this cell
     */
    @Override
    public void interact(Player player) {
        player.damagePlayer(2);
        player.updateScore(2);
        System.out.println("You fought a melee mutant! -3 HP");
        player.getGameEngine().clearCell(x, y); // Sets Cell as an EmptyCell
    }

    /**
     * @return The character representing the cell
     */
    @Override
    public String getIcon() {
        return "M";
    }
}
