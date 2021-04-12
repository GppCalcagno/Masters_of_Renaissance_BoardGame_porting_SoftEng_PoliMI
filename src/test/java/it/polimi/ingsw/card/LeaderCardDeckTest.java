package it.polimi.ingsw.card;

import it.polimi.ingsw.game.LeaderCardDeck;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Coins;
import it.polimi.ingsw.requirements.RequestedResources;
import it.polimi.ingsw.requirements.Requirements;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LeaderCardDeckTest {

    @Test
    void getLeaderCardList() throws IOException {
        LeaderCardDeck leaderCardDeck = new LeaderCardDeck();

        assertEquals(2, leaderCardDeck.getLeaderCardList(0).getVictoryPoints());
    }

    @Test
    void reqResourcesTest() throws IOException {
        LeaderCardDeck leaderCardDeck = new LeaderCardDeck();
        Requirements requirements = new RequestedResources(new Coins(), 1);

        assertEquals(requirements.getClass(), leaderCardDeck.getLeaderCardList(6).getCost().getClass());
    }

    @Test
    void sizeTest() throws IOException {
        LeaderCardDeck leaderCardDeck = new LeaderCardDeck();

        assertEquals(16, leaderCardDeck.getSize());
    }

    @Test
    void givetoPlayer() throws IOException {
        LeaderCardDeck leaderCardDeck = new LeaderCardDeck();
        Player player = new Player("Lillo");

        leaderCardDeck.givetoPlayer(0, player);
        assertFalse(player.getLeaderActionBox().isEmpty());
    }
}