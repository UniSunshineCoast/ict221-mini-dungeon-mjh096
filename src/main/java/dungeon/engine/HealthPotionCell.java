package dungeon.engine;

public class HealthPotionCell extends Cell {
    public HealthPotionCell(int x, int y) {
        super(x, y);
    }

    @Override
    public void interact(Player player) {
        player.updateHP(4); // cap at 10 in implementation
        // Potion disappears after use
    }

    @Override
    public String getIcon() {
        return "H";
    }
}
