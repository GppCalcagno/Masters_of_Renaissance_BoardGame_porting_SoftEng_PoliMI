package it.polimi.ingsw.marbles;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Stones;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GreyMarbleTest {

    @Test
    void addtoWarehouse() {
        Player player = new Player("Luca");
        Marbles marbles = new GreyMarble();

        assertEquals(0, player.getWarehouse().getNumResources(new Stones()));

        marbles.addtoWarehouse(player, 0);

        assertEquals(1, player.getWarehouse().getNumResources(new Stones()));
    }
}