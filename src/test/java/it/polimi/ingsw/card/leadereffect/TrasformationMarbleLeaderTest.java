package it.polimi.ingsw.card.leadereffect;

import Stub.LeaderCardDeckStub;
import it.polimi.ingsw.game.DevCardsDeck;
import it.polimi.ingsw.game.LeaderCardDeck;
import it.polimi.ingsw.player.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrasformationMarbleLeaderTest {
    @Test
    void doSpecialAbilityTestActivated() throws IOException {
        Player player = new Player("Gigi");
        LeaderCardDeck leaderCardDeck = new LeaderCardDeckStub();
        DevCardsDeck devCardsDeck = new DevCardsDeck();
        player.getSlotDevCards().insertCards(0, devCardsDeck.getDevCards(2,2));
        player.getSlotDevCards().insertCards(0, devCardsDeck.getDevCards(1,2));
        player.getSlotDevCards().insertCards(0, devCardsDeck.getDevCards(0,1));

        assertTrue(leaderCardDeck.getLeaderCardList(12).doSpecialAbility(player));
        assertTrue(leaderCardDeck.getLeaderCardList(12).getActivated());
    }

    @Test
    void doSpecialAbilityTestAdd() throws IOException {
        Player player = new Player("Gigi");
        LeaderCardDeck leaderCardDeck = new LeaderCardDeckStub();
        DevCardsDeck devCardsDeck = new DevCardsDeck();
        player.getSlotDevCards().insertCards(0, devCardsDeck.getDevCards(2,2));
        player.getSlotDevCards().insertCards(0, devCardsDeck.getDevCards(1,2));
        player.getSlotDevCards().insertCards(0, devCardsDeck.getDevCards(0,1));

        assertTrue(leaderCardDeck.getLeaderCardList(12).doSpecialAbility(player));
        assertFalse(player.getLeaderCardEffectWhiteMarble().isEmpty());
    }

    @Test
    void doSpecialAbilityTestNotActivated() throws IOException {
        Player player = new Player("Gigi");
        LeaderCardDeck leaderCardDeck = new LeaderCardDeckStub();
        DevCardsDeck devCardsDeck = new DevCardsDeck();
        player.getSlotDevCards().insertCards(0, devCardsDeck.getDevCards(2,2));
        player.getSlotDevCards().insertCards(0, devCardsDeck.getDevCards(1,2));
        player.getSlotDevCards().insertCards(0, devCardsDeck.getDevCards(0,1));
        leaderCardDeck.getLeaderCardList(12).setActivated();

        assertFalse(leaderCardDeck.getLeaderCardList(12).doSpecialAbility(player));
    }

    @Test
    void doSpecialAbilityTestNotRequirements() throws IOException {
        Player player = new Player("Gigi");
        LeaderCardDeck leaderCardDeck = new LeaderCardDeckStub();

        assertFalse(leaderCardDeck.getLeaderCardList(12).doSpecialAbility(player));
    }
}