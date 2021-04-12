package it.polimi.ingsw.marbles;


import StubGiovanni.PlayerStubMarbles;
import it.polimi.ingsw.producible.Resources;
import it.polimi.ingsw.producible.Shields;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlueMarbleTest {

    @Test
    void addtoWarehouse() {
        PlayerStubMarbles player = new PlayerStubMarbles("Giacomo");
        Marbles blue = new BlueMarble();
        Resources shield = new Shields();

        blue.addtoWarehouse(player, 1);

        // Voglio verificare che la risorsa aggiunta al Warehouse sia veramente uno Shields
        assertEquals(shield.getClass(), player.getWarehouse().getResourcesWarehouse(0, 0).getClass());
    }
}