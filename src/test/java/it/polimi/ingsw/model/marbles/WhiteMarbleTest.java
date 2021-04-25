package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhiteMarbleTest {

    @Test
    void addToWarehouseNotEmpty() throws ActiveVaticanReportException {
        Player player = new Player("Filomena");
        Resources resources = new Coins();
        player.addleaderCardEffectWhiteMarble(resources);
        Marbles marbles = new WhiteMarble();

        assertTrue(marbles.addtoWarehouse(player, 0));
        assertEquals(1, player.getWarehouse().getNumResources(resources));
    }

    @Test
    void addToWarehouseEmpty() throws ActiveVaticanReportException {
        Player player = new Player("Filomena");
        Marbles marbles = new WhiteMarble();

        assertTrue(marbles.addtoWarehouse(player, 0));
    }

    @Test
    void addToWarehouseFalse() throws ActiveVaticanReportException {
        Player player = new Player("Filomena");
        Marbles marbles = new WhiteMarble();
        Resources resources = new Coins();
        player.addleaderCardEffectWhiteMarble(resources);
        assertTrue(player.getWarehouse().checkInsertion(0, resources));

        assertFalse(marbles.addtoWarehouse(player, 1));
    }
}