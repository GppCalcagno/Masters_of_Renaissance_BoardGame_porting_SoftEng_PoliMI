package it.polimi.ingsw.model.card.leadereffect;

import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Coins;
import it.polimi.ingsw.model.producible.Stones;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtraProductionTest {

    @Test
    void activeExtraProduction() throws ActiveVaticanReportException {
        Player player= new Player("CrushTest");
        ExtraProduction test= new ExtraProduction(new Coins());

        test.activeExtraProduction(player,new Stones());

        assertEquals(player.getFaithMarker(),1);
        assertEquals(player.getStrongbox().getNumResources(new Stones()),1);

    }
}
