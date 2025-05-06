package dungeon.engine;

import java.io.Serializable;

/**
 * Represents a trap in the dungeon grid.
 * Stepping on this cell will cause damage to the player.
 */
public class TrapCell extends Cell implements Serializable {
    /**
     * Unique ID for serialization compatibility.
     * Used to ensure that object matches the version of the class.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Constructs a TrapCell at the given map coordinates.
     *
     * @param x the x-coordinate position of the cell
     * @param y the y-coordinate position of the cell
     */
    public TrapCell(int x, int y) {
        super(x, y);
    }

    /**
     * Performs action associated with this cell type.
     * Damages player as they interact with trap
     *
     * @param player Player interacting with this cell
     */
    @Override
    public void interact(Player player) {
        player.damagePlayer(1);
        System.out.println("You triggered a trap! -1 HP");
    }

    /**
     * @return The character representing the cell
     */
    @Override
    public String getIcon() {
        return "T";
    }
}
