package it.polimi.ingsw.game;

import it.polimi.ingsw.card.ColorCard;
import it.polimi.ingsw.game.DevCardsDeck;
import it.polimi.ingsw.player.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DevCardsDeckTest {
    @Test
    public void checkDeserialization() throws IOException {
        DevCardsDeck devCardsDeck = new DevCardsDeck();

        Assertions.assertEquals(ColorCard.YELLOW, devCardsDeck.getDevCards(0,2).getColorCard());
        assertNotEquals(ColorCard.GREEN, devCardsDeck.getDevCards(1,3).getColorCard());
    }

    @Test
    public void getDevCards() throws IOException {
        DevCardsDeck devCardsDeck = new DevCardsDeck();

        assertNotEquals(null, devCardsDeck.getDevCards(0,0));
        assertNotEquals(null, devCardsDeck.getDevCards(1,2));
        assertNotEquals(null, devCardsDeck.getDevCards(2,3));
    }


    @Test
    void removeDevCards() throws IOException {
        DevCardsDeck devCardsDeck = new DevCardsDeck();

        devCardsDeck.removeDevCards(2,0);
        devCardsDeck.removeDevCards(2,0);
        devCardsDeck.removeDevCards(2,0);
        devCardsDeck.removeDevCards(2,0);
        devCardsDeck.removeDevCards(2,0);

        assertEquals(null, devCardsDeck.getDevelopmentCardDeck()[2][0][3]);
        assertEquals(null, devCardsDeck.getDevelopmentCardDeck()[2][0][2]);
        assertEquals(null, devCardsDeck.getDevelopmentCardDeck()[2][0][1]);

    }

    @Test
    void removeOneCard() throws IOException {
        DevCardsDeck devCardsDeck = new DevCardsDeck();

        devCardsDeck.removeDevCards(2,0);
        assertEquals(null, devCardsDeck.getDevelopmentCardDeck()[2][0][3]);

    }

    @Test
    void purhcaseCardsofOneCard() throws IOException {
        DevCardsDeck devCardsDeck = new DevCardsDeck();
        Player player = new Player("Seppietta");

        assertTrue(devCardsDeck.purchaseCards(player, 0, 2, 0));
    }

    @Test
    void purchaseCardsofMultipleCards() throws IOException{
        DevCardsDeck devCardsDeck = new DevCardsDeck();
        Player player = new Player("Maccio");

        assertTrue(devCardsDeck.purchaseCards(player, 0, 2, 0));
        assertTrue(devCardsDeck.purchaseCards(player, 0, 1, 0));
        assertTrue(devCardsDeck.purchaseCards(player, 1, 2, 0));
        assertTrue(devCardsDeck.purchaseCards(player, 2, 2, 0));
        assertTrue(devCardsDeck.purchaseCards(player, 2, 1, 0));
        assertTrue(devCardsDeck.purchaseCards(player, 2, 0, 0));
    }

    @Test
    void getColumnFromColor() throws IOException {
        DevCardsDeck devCardsDeck = new DevCardsDeck();

        assertEquals(2, devCardsDeck.getColumnFromColor(ColorCard.YELLOW));
        assertNotEquals(1, devCardsDeck.getColumnFromColor(ColorCard.GREEN));
    }
}