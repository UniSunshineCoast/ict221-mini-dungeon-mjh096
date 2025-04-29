package dungeon.engine;

public class GoldCell extends Cell {
    public GoldCell(int x, int y) {
        super(x, y);
    }

    @Override
    public void interact(Player player) {
        player.updateScore(2);
        // Remove gold from map afterward (cell becomes empty)
    }

    @Override
    public String getIcon() {
        return "G";
    }
}
