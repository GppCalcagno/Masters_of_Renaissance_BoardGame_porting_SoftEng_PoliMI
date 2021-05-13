package it.polimi.ingsw.model.card.leadereffect;

import Stub.DevCardsStub;
import Stub.LeaderCardDeckStub;
import it.polimi.ingsw.model.exceptions.GameFinishedException;
import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.model.game.DevCardsDeck;
import it.polimi.ingsw.model.game.LeaderCardDeck;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrasformationMarbleLeaderTest {
    @Test
    void doSpecialAbilityTestActivated() throws IOException, GameFinishedException {
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
    void doSpecialAbilityTestAdd() throws IOException, GameFinishedException {
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
    void doSpecialAbilityTestNotActivated() throws IOException, GameFinishedException {
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
    @Test
    void showCli() throws IOException, NegativeQuantityExceptions {
        LeaderCardDeck leaderCardDeck =  new LeaderCardDeckStub();

        leaderCardDeck.getLeaderCardList(12).showCli();

    }
}