package it.polimi.ingsw.card.leadereffect;

import Stub.LeaderCardDeckStub;
import it.polimi.ingsw.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.game.LeaderCardDeck;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Coins;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ChestLeaderTest {
    //Ho usato uno stub LeaderCardDeck nel cui costruttore non faccio shuffle e per questo so le posizioni delle carte nella lista

    @Test
    void doSpecialAbilityTestActivated() throws IOException, NegativeQuantityExceptions {
        Player playerTest = new Player("Giocatore1");
        LeaderCardDeck leaderCardDeck =  new LeaderCardDeckStub();
        playerTest.getStrongbox().updateResources(new Coins(), 5);

        assertTrue(leaderCardDeck.getLeaderCardList(4).doSpecialAbility(playerTest));
        assertTrue(leaderCardDeck.getLeaderCardList(4).getActivated());
    }

    @Test
    void doSpecialAbilityTestAdd() throws IOException, NegativeQuantityExceptions {
        Player playerTest = new Player("Giocatore1");
        LeaderCardDeck leaderCardDeck =  new LeaderCardDeckStub();
        playerTest.getStrongbox().updateResources(new Coins(), 5);

        assertTrue(leaderCardDeck.getLeaderCardList(4).doSpecialAbility(playerTest));
        assertFalse(playerTest.getWarehouse().getLeaderCardEffect().isEmpty());
    }

    @Test
    void doSpecialAbilityTestNotActivated() throws IOException {
        Player playerTest = new Player("Giocatore2");
        LeaderCardDeck leaderCardDeck = new LeaderCardDeckStub();
        leaderCardDeck.getLeaderCardList(4).setActivated();

        assertFalse(leaderCardDeck.getLeaderCardList(4).doSpecialAbility(playerTest));
    }

    @Test
    void doSpecialAbilityTestNotRequirements() throws IOException {
        Player playerTest = new Player("Giocatore2");
        LeaderCardDeck leaderCardDeck = new LeaderCardDeckStub();

        assertFalse(leaderCardDeck.getLeaderCardList(4).doSpecialAbility(playerTest));
    }
}