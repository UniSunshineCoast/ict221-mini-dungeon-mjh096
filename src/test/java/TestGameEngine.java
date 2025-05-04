import dungeon.engine.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestGameEngine {
    /**
     * Test getSize in GameEngine.Java returns the proper grid size
     */
    @Test
    void testGetSize() {
        GameEngine ge = new GameEngine(10);
        assertEquals(10, ge.getSize());

    }

    /**
     * Tests the Win condition by player passing a ladder.
     */
    @Test
    void testCheckWin() {
        GameEngine ge = new GameEngine(10);
        Player pl = new Player("Test",0,0);
        ge.setPlayer(pl);

        ge.getMap()[0][0] = new LadderCell(0, 0);
        assertTrue(ge.checkWin());
    }

    /**
     * Checks Lose condition by HP dropping below the minimum amount
     */
    @Test
    void testCheckLoseHP() {
        GameEngine ge = new GameEngine(10);
        Player pl = new Player("Test",0,0);
        ge.setPlayer(pl);

        pl.damagePlayer(10);
        assertTrue(ge.checkLose());
    }

    /**
     * Checks Lose condition by step count passing limit
     */
    @Test
    void testCheckLoseSteps() {
        GameEngine ge = new GameEngine(10);
        Player pl = new Player("Test",0,0);
        ge.setPlayer(pl);

        for (int i = 0; i < 100; i++) {
            pl.incrementSteps();
        }
        assertTrue(ge.checkLose());
    }

    /**
     * Tests Player Movement
     */
    @Test
    void testMove() {
        GameEngine ge = new GameEngine(10);
        Player pl = new Player("Test",0,0);
        ge.setPlayer(pl);
        Cell[][] map = ge.getMap();
        map[0][1] = new EmptyCell(0, 1);

        ge.moveRight();

        assertEquals(0, pl.getX());
        assertEquals(1, pl.getY());

        ge.moveLeft();

        assertEquals(0, pl.getX());
        assertEquals(0, pl.getY());

        ge.moveDown();

        assertEquals(1, pl.getX());
        assertEquals(0, pl.getY());

        ge.moveUp();

        assertEquals(0, pl.getX());
        assertEquals(0, pl.getY());
    }

    /**
     * Checks the Map is being generated with the minimum required cells
     */
    @Test
    void testMapGeneration() {
        GameEngine ge = new GameEngine(10);
        Player pl = new Player("Test",0,0);
        ge.setPlayer(pl);

        ge.generateMap();
        Cell[][] map = ge.getMap();

        int goldCount = 0;
        int trapCount = 0;
        int ladderCount = 0;
        int potionCount = 0;
        int meleeCount = 0;
        int rangedCount = 0;

        for (Cell[] row : map) {
            for (Cell c : row) {
                if (c instanceof GoldCell) goldCount++;
                else if (c instanceof TrapCell) trapCount++;
                else if (c instanceof LadderCell) ladderCount++;
                else if (c instanceof HealthPotionCell) potionCount++;
                else if (c instanceof MeleeMutantCell) meleeCount++;
                else if (c instanceof RangedMutantCell) rangedCount++;
            }
        }

        assertTrue(ladderCount >= 1, "At least 1 LadderCell should be placed.");
        assertTrue(goldCount >= 2, "At least 2 GoldCells should be placed.");
        assertTrue(trapCount >= 2, "At least 2 TrapCells should be placed.");
        assertTrue(potionCount >= 1, "At least 1 HealthPotionCell should be placed.");
        assertTrue(meleeCount >= 1, "At least 1 MeleeMutantCell should be placed.");
        assertTrue(rangedCount >= 1, "At least 1 RangedMutantCell should be placed.");
    }
}
