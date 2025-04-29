package dungeon.engine;

public class WallCell extends Cell {
    public WallCell(int x, int y) {
        super(x, y);
    }

    @Override
    public void interact(Player player) {
        // Block movement (possibly revert)
    }

    @Override
    public String getIcon() {
        return "#";
    }
}
