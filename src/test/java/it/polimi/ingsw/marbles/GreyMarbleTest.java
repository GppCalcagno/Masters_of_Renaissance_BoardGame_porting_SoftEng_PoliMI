package it.polimi.ingsw.marbles;

import StubGiovanni.PlayerStubMarbles;
import it.polimi.ingsw.producible.Resources;
import it.polimi.ingsw.producible.Stones;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GreyMarbleTest {

    @Test
    void addtoWarehouse() {
        PlayerStubMarbles player = new PlayerStubMarbles("Aldo");
        Marbles grey = new GreyMarble();
        Resources stones = new Stones();

        grey.addtoWarehouse(player, 1);

        // Voglio verificare che la risorsa aggiunta al Warehouse sia veramente una Stone
        assertEquals(stones.getClass(), player.getWarehouse().getResourcesWarehouse(0, 0).getClass());
    }
}