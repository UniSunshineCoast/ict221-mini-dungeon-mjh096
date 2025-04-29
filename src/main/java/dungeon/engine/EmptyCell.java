package dungeon.engine;

public class EmptyCell extends Cell {
    public EmptyCell(int x, int y) {
        super(x, y);
    }

    @Override
    public void interact(Player player) {
        // Nothing happens
    }

    @Override
    public String getIcon() {
        return "_";
    }
}
