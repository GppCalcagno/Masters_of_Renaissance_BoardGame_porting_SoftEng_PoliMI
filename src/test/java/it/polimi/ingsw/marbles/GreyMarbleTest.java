package it.polimi.ingsw.marbles;

import it.polimi.ingsw.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Stones;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GreyMarbleTest {

    @Test
    void addtoWarehouseTrue() throws ActiveVaticanReportException {
        Player player = new Player("Luca");
        Marbles marbles = new GreyMarble();

        assertEquals(0, player.getWarehouse().getNumResources(new Stones()));
        assertTrue(marbles.addtoWarehouse(player, 0));
        assertEquals(1, player.getWarehouse().getNumResources(new Stones()));
    }

    @Test
    void addtoWarehouseFalse() throws ActiveVaticanReportException {
        Player player = new Player("Luca");
        Marbles marbles = new GreyMarble();

        assertTrue(player.getWarehouse().checkInsertion(0, new Stones()));
        assertFalse(marbles.addtoWarehouse(player, 1));
    }
}