package it.polimi.ingsw.marbles;

import it.polimi.ingsw.StubGiovanni.PlayerStub;
import it.polimi.ingsw.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedMarbleTest {

    @Test
    void addtoWarehouse() {
        Player playerStub = new Player("Giovanni");
        Marbles marbles = new RedMarble();

        assertEquals(0, playerStub.getFaithMarker());
        marbles.addtoWarehouse(playerStub);
        assertEquals(1, playerStub.getFaithMarker());
    }
}
