package it.polimi.ingsw.marbles;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Coins;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class YellowMarbleTest {

    @Test
    void addtoWarehouse() {
        Player player = new Player("Luca");
        Marbles marbles = new YellowMarble();

        assertEquals(0, player.getWarehouse().getNumResources(new Coins()));

        marbles.addtoWarehouse(player, 0);

        assertEquals(1, player.getWarehouse().getNumResources(new Coins()));
    }
}