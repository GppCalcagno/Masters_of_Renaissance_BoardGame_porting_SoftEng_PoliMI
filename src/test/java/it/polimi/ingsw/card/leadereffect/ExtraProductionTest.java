package it.polimi.ingsw.card.leadereffect;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Coins;
import it.polimi.ingsw.producible.Stones;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtraProductionTest {

    @Test
    void activeExtraProduction() {
        Player player= new Player("CrushTest");
        ExtraProduction test= new ExtraProduction(new Coins());

        test.activeExtraProduction(player,new Stones());

        assertEquals(player.getFaithMarker(),1);
        assertEquals(player.getStrongbox().getNumResources(new Stones()),1);

    }
}
