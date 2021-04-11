package it.polimi.ingsw.marbles;

import it.polimi.ingsw.StubGiovanni.PlayerStubMarbles;
import it.polimi.ingsw.producible.Resources;
import it.polimi.ingsw.producible.Servants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PurpleMarbleTest {

    @Test
    void addtoWarehouse() {
        PlayerStubMarbles player = new PlayerStubMarbles("Giacomo");
        Marbles purple = new PurpleMarble();
        Resources servants = new Servants();

        purple.addtoWarehouse(player);

        // Voglio verificare che la risorsa aggiunta al Warehouse sia veramente un Servants
        assertEquals(servants.getClass(), player.getWarehouse().getResourcesWarehouse(0, 0).getClass());
    }
}