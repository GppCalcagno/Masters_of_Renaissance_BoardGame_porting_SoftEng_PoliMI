package it.polimi.ingsw.player;

import it.polimi.ingsw.game.DevCardsDeck;
import it.polimi.ingsw.producible.Coins;
import it.polimi.ingsw.producible.Resources;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SlotDevCardsTest {

    @Test
    void maxLevelPurchase() throws IOException {
        SlotDevCards s = new SlotDevCards();
        DevCardsDeck d = new DevCardsDeck();
        s.insertCards(0, d.getDevCards(2,0));

        assertFalse(s.maxLevelPurchase(d.getDevCards(0, 0)));
    }

    @Test
    void insertCards() throws IOException {
        SlotDevCards s = new SlotDevCards();
        DevCardsDeck d = new DevCardsDeck();
        assertTrue(s.insertCards(0, d.getDevCards(2,0)));
        assertTrue(s.insertCards(0, d.getDevCards(1,0)));
        assertFalse(s.insertCards(1, d.getDevCards(1,0)));

    }

    @Test
    void checkUsage() throws IOException {
        SlotDevCards s = new SlotDevCards();
        DevCardsDeck d = new DevCardsDeck();
        s.insertCards(0, d.getDevCards(2,0));
        s.insertCards(0, d.getDevCards(1,0));
        s.insertCards(1, d.getDevCards(2,1));

        assertTrue(s.checkUsage(s.getDevCards(1,0)));
        assertFalse(s.checkUsage(s.getDevCards(0,0)));
        assertTrue(s.checkUsage(s.getDevCards(0,1)));

    }

    @Test
    void countTotalNumberDevCards() throws IOException {
        SlotDevCards s = new SlotDevCards();
        DevCardsDeck d = new DevCardsDeck();
        s.insertCards(0, d.getDevCards(2,0));
        s.insertCards(0, d.getDevCards(1,0));
        s.insertCards(1, d.getDevCards(2,1));

        assertEquals(3, s.countTotalNumberDevCards());

    }
}