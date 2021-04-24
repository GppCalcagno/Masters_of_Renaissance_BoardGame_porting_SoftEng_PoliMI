package it.polimi.ingsw.marbles;

import it.polimi.ingsw.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.player.Player;
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
}
