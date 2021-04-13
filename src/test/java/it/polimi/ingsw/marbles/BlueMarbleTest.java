package it.polimi.ingsw.marbles;


import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Shields;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlueMarbleTest {

    @Test
    void addtoWarehouse() {
        Player player = new Player("Luca");
        Marbles marbles = new BlueMarble();

        assertEquals(0, player.getWarehouse().getNumResources(new Shields()));

        marbles.addtoWarehouse(player, 0);

        assertEquals(1, player.getWarehouse().getNumResources(new Shields()));
    }
}