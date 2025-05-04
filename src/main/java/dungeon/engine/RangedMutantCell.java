package dungeon.engine;

import java.util.Random;

/**
 * Represents a Ranged Mutant in the dungeon grid.
 * This cell can randomly attack player from 2 tiles away, stepping on it increases score.
 */
public class RangedMutantCell extends Cell {
    /**
     * Allows the RangedMutantCell to randomly decide if it will attack or not.
     */
    private Random random = new Random();

    /**
     * Constructs a RangedMutantCell at the given map coordinates.
     *
     * @param x the x-coordinate position of the cell
     * @param y the y-coordinate position of the cell
     */
    public RangedMutantCell(int x, int y) {
        super(x, y);
    }

    /**
     * Performs action associated with this cell type.
     *
     * @param player Player interacting with this cell
     */
    @Override
    public void interact(Player player) {
        // Direct interaction
        player.updateScore(2);
    }

    /**
     * Attempts to attack player with a random chance of hitting.
     * If successful, updates players health to record to the damage taken.
     *
     * @param player Player interacting with this cell
     */
    public void tryAttack(Player player) {
        if (random.nextBoolean()) {
            player.updateHP(-2);
        }
    }

    /**
     * @return The character representing the cell
     */
    @Override
    public String getIcon() {
        return "R";
    }
}
