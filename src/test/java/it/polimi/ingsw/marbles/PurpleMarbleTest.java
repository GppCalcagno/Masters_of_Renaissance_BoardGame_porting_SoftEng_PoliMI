package it.polimi.ingsw.marbles;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Servants;
import it.polimi.ingsw.producible.Stones;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PurpleMarbleTest {

    @Test
    void addtoWarehouseTrue() {
        Player player = new Player("Luca");
        Marbles marbles = new PurpleMarble();

        assertEquals(0, player.getWarehouse().getNumResources(new Servants()));
        assertTrue(marbles.addtoWarehouse(player, 0));
        assertEquals(1, player.getWarehouse().getNumResources(new Servants()));
    }

    @Test
    void addtoWarehouseFalse() {
        Player player = new Player("Luca");
        Marbles marbles = new PurpleMarble();

        assertTrue(player.getWarehouse().checkInsertion(0, new Servants()));
        assertFalse(marbles.addtoWarehouse(player, 1));
    }
}