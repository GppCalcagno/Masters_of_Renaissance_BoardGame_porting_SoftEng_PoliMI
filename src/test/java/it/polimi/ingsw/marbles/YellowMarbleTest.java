package it.polimi.ingsw.marbles;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Coins;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class YellowMarbleTest {

    @Test
    void addtoWarehouseTrue() {
        Player player = new Player("Luca");
        Marbles marbles = new YellowMarble();

        assertEquals(0, player.getWarehouse().getNumResources(new Coins()));
        assertTrue(marbles.addtoWarehouse(player, 0));
        assertEquals(1, player.getWarehouse().getNumResources(new Coins()));
    }

    @Test
    void addtoWarehouseFalse() {
        Player player = new Player("Luca");
        Marbles marbles = new YellowMarble();

        assertTrue(player.getWarehouse().checkInsertion(0, new Coins()));
        assertFalse(marbles.addtoWarehouse(player, 1));
    }
}