package dungeon.engine;

import java.io.Serializable;

/**
 * Represents a ladder cell in the dungeon grid.
 * This cell advances player to the next level or exits the game .
 */
public class LadderCell extends Cell implements Serializable {
    /**
     * Unique ID for serialization compatibility.
     * Used to ensure that object matches the version of the class.
     */
    private static final long serialVersionUID = 1L;
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
        GameEngine engine = player.getGameEngine();
        int currentLevel = engine.getCurrentLevel();

        if (currentLevel == 1) {
            engine.switchLevel(2);
        } else if (currentLevel == 2) {
            engine.setPlayerHasWon(true);
        }
    }

    /**
     * @return The character representing the cell
     */
    @Override
    public String getIcon() {
        return "L";
    }
}
