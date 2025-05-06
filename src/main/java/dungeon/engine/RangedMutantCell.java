package dungeon.engine;

import java.io.Serializable;
import java.util.Random;

/**
 * Represents a Ranged Mutant in the dungeon grid.
 * This cell can randomly attack player from 2 tiles away, stepping on it increases score.
 */
public class RangedMutantCell extends Cell implements Serializable {
    /**
     * Unique ID for serialization compatibility.
     * Used to ensure that object matches the version of the class.
     */
    private static final long serialVersionUID = 1L;
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
        System.out.println("You fought a ranged mutant! +2 Score");
        player.getGameEngine().clearCell(x, y); // Sets Cell as an EmptyCell
    }

    /**
     * Attempts to attack player with a random chance of hitting.
     * If successful, updates players health to record to the damage taken.
     *
     * @param player Player interacting with this cell
     */
    public void tryAttack(Player player) {
        int dx = Math.abs(player.getX() - this.x);
        int dy = Math.abs(player.getY() - this.y);

        // Only hit if exactly 2 tiles away
        if ((dx == 2 && dy == 0) || (dx == 0 && dy == 2)) {
            if (new Random().nextBoolean()) {
                player.damagePlayer(2);
                System.out.println("A ranged mutant shot you from (" + x + "," + y + ")! -2 HP");
            }
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
