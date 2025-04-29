package dungeon.engine;

import java.util.Random;

public class RangedMutantCell extends Cell {
    private Random random = new Random();

    public RangedMutantCell(int x, int y) {
        super(x, y);
    }

    @Override
    public void interact(Player player) {
        // Direct interaction
        player.updateScore(2);
    }

    public void tryAttack(Player player) {
        if (random.nextBoolean()) {
            player.updateHP(-2);
        }
    }

    @Override
    public String getIcon() {
        return "R";
    }
}
