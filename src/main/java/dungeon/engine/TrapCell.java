package dungeon.engine;

public class TrapCell extends Cell {
    public TrapCell(int x, int y) {
        super(x, y);
    }

    @Override
    public void interact(Player player) {
        player.updateHP(-2);
        // Trap remains
    }

    @Override
    public String getIcon() {
        return "T";
    }
}
