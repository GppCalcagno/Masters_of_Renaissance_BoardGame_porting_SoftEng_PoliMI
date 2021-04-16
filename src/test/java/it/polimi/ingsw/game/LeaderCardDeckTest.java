package it.polimi.ingsw.game;

import it.polimi.ingsw.game.LeaderCardDeck;
import it.polimi.ingsw.player.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LeaderCardDeckTest {

    @Test
    void sizeTest() throws IOException {
        LeaderCardDeck leaderCardDeck = new LeaderCardDeck();

        assertEquals(16, leaderCardDeck.getSize());
    }

    @Test
    void givetoPlayer () throws IOException {
        LeaderCardDeck leaderCardDeck = new LeaderCardDeck();
        Player player = new Player("Antonino");

        leaderCardDeck.givetoPlayer(0, player);
        assertFalse(player.getLeaderActionBox().isEmpty());
        assertNotEquals(player.getLeaderActionBox().get(0), leaderCardDeck.getLeaderCardList(0));
    }
}