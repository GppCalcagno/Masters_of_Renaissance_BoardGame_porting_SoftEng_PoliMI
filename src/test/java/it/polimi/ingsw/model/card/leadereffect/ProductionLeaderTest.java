package it.polimi.ingsw.model.card.leadereffect;

import Stub.LeaderCardDeckStub;
import it.polimi.ingsw.model.game.DevCardsDeck;
import it.polimi.ingsw.model.game.LeaderCardDeck;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ProductionLeaderTest {
    //Ho usato uno stub LeaderCardDeck nel cui costruttore non faccio shuffle e per questo so le posizioni delle carte nella lista

    @Test
    void doSpecialAbilityTestActivated() throws IOException {
        Player playerTest = new Player("Giocatore1");
        LeaderCardDeck leaderCardDeck =  new LeaderCardDeckStub();
        DevCardsDeck devCardsDeck = new DevCardsDeck();
        playerTest.getSlotDevCards().insertCards(0, devCardsDeck.getDevCards(2, 3));
        playerTest.getSlotDevCards().insertCards(0, devCardsDeck.getDevCards(1, 2));
        playerTest.getSlotDevCards().insertCards(1, devCardsDeck.getDevCards(2, 3));
        playerTest.getSlotDevCards().insertCards(1, devCardsDeck.getDevCards(1, 2));

        assertTrue(leaderCardDeck.getLeaderCardList(8).doSpecialAbility(playerTest));
        assertTrue(leaderCardDeck.getLeaderCardList(8).getActivated());
    }

    @Test
    void doSpecialAbilityTestAdd() throws IOException {
        Player playerTest = new Player("Giocatore1");
        LeaderCardDeck leaderCardDeck =  new LeaderCardDeckStub();
        DevCardsDeck devCardsDeck = new DevCardsDeck();
        playerTest.getSlotDevCards().insertCards(0, devCardsDeck.getDevCards(2, 3));
        playerTest.getSlotDevCards().insertCards(0, devCardsDeck.getDevCards(1, 2));
        playerTest.getSlotDevCards().insertCards(1, devCardsDeck.getDevCards(2, 3));
        playerTest.getSlotDevCards().insertCards(1, devCardsDeck.getDevCards(1, 2));

        assertTrue(leaderCardDeck.getLeaderCardList(8).doSpecialAbility(playerTest));
        assertFalse(playerTest.getSlotDevCards().getLeaderCardEffect().isEmpty());
    }

    @Test
    void doSpecialAbilityTestNotActivated() throws IOException {
        Player playerTest = new Player("Giocatore1");
        LeaderCardDeck leaderCardDeck = new LeaderCardDeckStub();
        leaderCardDeck.getLeaderCardList(8).setActivated();

        assertFalse(leaderCardDeck.getLeaderCardList(8).doSpecialAbility(playerTest));
    }

    @Test
    void doSpecialAbilityTestNotRequirements() throws IOException {
        Player playerTest = new Player("Giocatore2");
        LeaderCardDeck leaderCardDeck = new LeaderCardDeckStub();

        assertFalse(leaderCardDeck.getLeaderCardList(0).doSpecialAbility(playerTest));
    }
}