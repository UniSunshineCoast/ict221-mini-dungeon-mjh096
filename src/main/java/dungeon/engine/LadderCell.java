package dungeon.engine;

public class LadderCell extends Cell {
    public LadderCell(int x, int y) {
        super(x, y);
    }

    @Override
    public void interact(Player player) {
        // Advance to next level or end game
    }

    @Override
    public String getIcon() {
        return "L";
    }
}
