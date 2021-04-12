package it.polimi.ingsw.marbles;

import StubGiovanni.PlayerStubMarbles;
import it.polimi.ingsw.producible.Coins;
import it.polimi.ingsw.producible.Resources;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhiteMarbleTest {

    @Test
    void addtoWarehouse() {
        PlayerStubMarbles player = new PlayerStubMarbles("Giaco");
        Marbles white = new WhiteMarble();
        Resources coin = new Coins();

        player.addleaderCardEffectWhiteMarble(coin);

        white.addtoWarehouse(player, 1);

        assertEquals(coin, player.getWarehouse().getResourcesWarehouse(0, 0));
    }
}