package dungeon.engine;

public class MeleeMutantCell extends Cell {
    public MeleeMutantCell(int x, int y) {
        super(x, y);
    }

    @Override
    public void interact(Player player) {
        player.updateHP(-2);
        player.updateScore(2);
        // Mutant removed after interaction
    }

    @Override
    public String getIcon() {
        return "M";
    }
}
