import dungeon.engine.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPlayer {
    /**
     * Confirms the default values for the player are correct
     */
    @Test
    void testPlayerValues() {
        Player p = new Player("Test",1,1);
        assertEquals("Test", p.getName());
        assertEquals(1, p.getX());
        assertEquals(1, p.getY());
        assertEquals(10, p.getHP()); // starting HP
        assertEquals(0, p.getScore());
        assertEquals(0, p.getStepsTaken());
    }

    /**
     * Tests the score function
     */
    @Test
    void testScore() {
        Player p = new Player("Test", 1, 1);
        p.updateScore(5);
        assertEquals(5, p.getScore());
    }

    /**
     * Tests the incrementSteps function used with the step counter
     */
    @Test
    void testSteps() {
        Player p = new Player("Test", 1, 1);
        p.incrementSteps();
        assertEquals(1, p.getStepsTaken());
    }

    /**
     * Test adding and removing Health Points from player
     */
    @Test
    void testHP() {
        Player p = new Player("Test", 1, 1);
        p.updateHP(3);
        assertEquals(3, p.getHP());

        p.healPlayer(3);
        assertEquals(6, p.getHP());

        p.damagePlayer(2);
        assertEquals(4, p.getHP());
    }
}
