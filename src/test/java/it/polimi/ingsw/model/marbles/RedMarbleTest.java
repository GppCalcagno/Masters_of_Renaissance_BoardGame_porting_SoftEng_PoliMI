package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.model.exceptions.OverflowQuantityExcepions;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedMarbleTest {

    @Test
    void addtoWarehouseTrue() throws ActiveVaticanReportException {
        Player playerStub = new Player("Giova");
        Marbles marbles = new RedMarble();

        assertEquals(0, playerStub.getFaithMarker());
        assertTrue(marbles.addtoWarehouse(playerStub, 0));
        assertEquals(1, playerStub.getFaithMarker());
    }

    @Test
    void addtoWarehouseFalse() throws ActiveVaticanReportException {
        Player playerStub = new Player("Giova");
        Marbles marbles = new RedMarble();

        for (int i = 0; i < 25; i++){
            try {
                playerStub.increasefaithMarker();
            }
            catch (ActiveVaticanReportException activeVaticanReportException) {

            }
        }
        assertFalse(marbles.addtoWarehouse(playerStub, 0));
    }

    @Test
    void addToExtraChest() throws NegativeQuantityExceptions, OverflowQuantityExcepions {
        Player playerStub = new Player("Giova");
        Marbles marbles = new RedMarble();

        assertFalse(marbles.addToExtraChest(playerStub));
    }
}
