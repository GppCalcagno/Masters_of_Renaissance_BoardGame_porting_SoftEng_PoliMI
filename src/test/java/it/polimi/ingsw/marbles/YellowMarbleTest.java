package it.polimi.ingsw.marbles;

import it.polimi.ingsw.StubGiovanni.PlayerStubMarbles;
import it.polimi.ingsw.producible.Coins;
import it.polimi.ingsw.producible.Resources;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class YellowMarbleTest {

    @Test
    void addtoWarehouse() {
        PlayerStubMarbles player = new PlayerStubMarbles("Giaco");
        Marbles yellow = new YellowMarble();
        Resources coin = new Coins();

        yellow.addtoWarehouse(player);

        // Voglio verificare che la risorsa aggiunta al Warehouse sia veramente un Coin
        assertEquals(coin.getClass(), player.getWarehouse().getResourcesWarehouse(0, 0).getClass());
    }
}