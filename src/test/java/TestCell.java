import dungeon.engine.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCell {

    @Test
    void testGoldCell() {
        Player pl = new Player("Test", 0, 0);
        GoldCell cl = new GoldCell(0, 0);

        cl.interact(pl);
        assertEquals(2, pl.getScore());
    }
}
