package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Servants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PurpleMarbleTest {

    @Test
    void addtoWarehouseTrue() throws ActiveVaticanReportException {
        Player player = new Player("Luca");
        Marbles marbles = new PurpleMarble();

        assertEquals(0, player.getWarehouse().getNumResources(new Servants()));
        assertTrue(marbles.addtoWarehouse(player, 0));
        assertEquals(1, player.getWarehouse().getNumResources(new Servants()));
    }

    @Test
    void addtoWarehouseFalse() throws ActiveVaticanReportException {
        Player player = new Player("Luca");
        Marbles marbles = new PurpleMarble();

        assertTrue(player.getWarehouse().checkInsertion(0, new Servants()));
        assertFalse(marbles.addtoWarehouse(player, 1));
    }
}