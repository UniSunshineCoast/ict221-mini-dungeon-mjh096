import dungeon.engine.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCell {

    /**
     * Tests Gold Cell, confirms interact provides an expected score change for player
     */
    @Test
    void testGoldCell() {
        GameEngine ge = new GameEngine(10);
        Player pl = new Player("Test",0,0);
        ge.setPlayer(pl);
        GoldCell cl = new GoldCell(0, 0);

        cl.interact(pl);
        assertEquals(2, pl.getScore());
    }

    /**
     *
     */
    @Test
    void testHealthPotionCell() {
        GameEngine ge = new GameEngine(10);
        Player pl = new Player("Test",0,0);
        ge.setPlayer(pl);
        HealthPotionCell cl = new HealthPotionCell(0, 0);

        cl.interact(pl);
        assertEquals(14, pl.getHP());
    }

    /**
     * Tests Melee Mutant interaction returns appropriate HP and Score changes
     */
    @Test
    void testMeleeMutantCell() {
        GameEngine ge = new GameEngine(10);
        Player pl = new Player("Test",0,0);
        ge.setPlayer(pl);
        MeleeMutantCell cl = new MeleeMutantCell(0, 0);

        cl.interact(pl);
        assertEquals(8, pl.getHP());
        assertEquals(2, pl.getScore());
    }

    /**
     * Tests Ranged Mutant interaction returns appropriate Score changes
     */
    @Test
    void testRangedMutantCell() {
        GameEngine ge = new GameEngine(10);
        Player pl = new Player("Test",0,0);
        ge.setPlayer(pl);
        RangedMutantCell cl = new RangedMutantCell(0, 0);

        cl.interact(pl);
        assertEquals(2, pl.getScore());
    }

    /**
     * Test Trap Cell provides damaged reduction appropriately
     */
    @Test
    void testTrapCell() {
        GameEngine ge = new GameEngine(10);
        Player pl = new Player("Test",0,0);
        ge.setPlayer(pl);
        TrapCell cl = new TrapCell(0, 0);

        cl.interact(pl);
        assertEquals(8, pl.getHP());
    }
}
