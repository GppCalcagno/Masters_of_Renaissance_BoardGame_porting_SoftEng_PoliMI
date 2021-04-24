package it.polimi.ingsw.marbles;


import it.polimi.ingsw.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Shields;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlueMarbleTest {

    @Test
    void addtoWarehouseTrue() throws ActiveVaticanReportException {
        Player player = new Player("Luca");
        Marbles marbles = new BlueMarble();

        assertEquals(0, player.getWarehouse().getNumResources(new Shields()));
        assertTrue(marbles.addtoWarehouse(player, 0));
        assertEquals(1, player.getWarehouse().getNumResources(new Shields()));
    }

    @Test
    void addtoWarehouseFalse() throws ActiveVaticanReportException {
        Player player = new Player("Luca");
        Marbles marbles = new BlueMarble();

        assertTrue(player.getWarehouse().checkInsertion(0, new Shields()));
        assertFalse(marbles.addtoWarehouse(player, 1));

    }
}