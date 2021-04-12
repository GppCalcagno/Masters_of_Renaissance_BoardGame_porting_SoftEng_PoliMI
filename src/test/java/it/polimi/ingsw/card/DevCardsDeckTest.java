package it.polimi.ingsw.card;

import StubGiovanni.PlayerStubDevCardsDeck;
import it.polimi.ingsw.game.DevCardsDeck;
import it.polimi.ingsw.player.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class DevCardsDeckTest {
    @Test
    public void checkDeserialization() throws IOException {
        DevCardsDeck devCardsDeck = new DevCardsDeck();

        assertEquals(ColorCard.YELLOW, devCardsDeck.getDevCards(0,2).getColorCard());
        assertNotEquals(ColorCard.GREEN, devCardsDeck.getDevCards(1,3).getColorCard());
    }

    @Test
    public void getDevCards() throws IOException {
        DevCardsDeck devCardsDeck = new DevCardsDeck();

        assertNotEquals(null, devCardsDeck.getDevCards(0,0));
    }

    @Test
    void removeDevCards() throws IOException {
        DevCardsDeck devCardsDeck = new DevCardsDeck();

        assertTrue(devCardsDeck.removeDevCards(0,0));
    }

    @Test
    void purhcaseCards() throws IOException {
        DevCardsDeck devCardsDeck = new DevCardsDeck();
        Player player = new PlayerStubDevCardsDeck("Seppietta");

        assertTrue(devCardsDeck.purchaseCards(player, 0, 0, 0));
    }

    @Test
    void getColumnFromColor() throws IOException {
        DevCardsDeck devCardsDeck = new DevCardsDeck();

        assertEquals(2, devCardsDeck.getColumnFromColor(ColorCard.YELLOW));
        assertNotEquals(1, devCardsDeck.getColumnFromColor(ColorCard.GREEN));
    }
}