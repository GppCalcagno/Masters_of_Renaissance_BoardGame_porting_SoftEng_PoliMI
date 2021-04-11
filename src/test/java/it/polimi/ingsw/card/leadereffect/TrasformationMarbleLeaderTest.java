package it.polimi.ingsw.card.leadereffect;

import it.polimi.ingsw.StubGiovanni.PlayerStub;
import it.polimi.ingsw.StubGiovanni.RequestedResourcesStub;
import it.polimi.ingsw.card.LeaderAction;
import it.polimi.ingsw.producible.Coins;
import it.polimi.ingsw.producible.Resources;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrasformationMarbleLeaderTest {
    @Test
    void doSpecialAbilityTestActivated() {
        PlayerStub playerTest = new PlayerStub("Giocatore1");
        Resources coin = new Coins();
        RequestedResourcesStub requirementsLeader = new RequestedResourcesStub(coin, 1);
        LeaderAction leaderActionTest = new TrasformationMarbleLeader();

        leaderActionTest.doSpecialAbility(playerTest);
        assertTrue(leaderActionTest.getActivated());
    }

    @Test
    void doSpecialAbilityTestAdd() {
        PlayerStub playerTest = new PlayerStub("Giocatore2");
        Resources coin = new Coins();
        RequestedResourcesStub requirementsLeader = new RequestedResourcesStub(coin, 1);
        LeaderAction leaderActionTest = new TrasformationMarbleLeader();

        leaderActionTest.doSpecialAbility(playerTest);
        assertTrue(playerTest.getSlotDevCards().getLeaderCardEffect().contains(coin));
    }
}