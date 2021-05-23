package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.exceptions.GameFinishedException;
import it.polimi.ingsw.model.game.DevCardsDeck;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ViewSlotDevCardsTest {

    @Test
    void maxLevelPurchase() throws IOException, GameFinishedException {
        SlotDevCards s = new SlotDevCards();
        DevCardsDeck d = new DevCardsDeck();
        s.insertCards(0, d.getDevCards(2,0));

        assertFalse(s.canBuyDevCard(d.getDevCards(0, 0),0));
    }

    @Test
    void insertCards() throws IOException, GameFinishedException {
        SlotDevCards s = new SlotDevCards();
        DevCardsDeck d = new DevCardsDeck();
        assertTrue(s.insertCards(0, d.getDevCards(2,0)));
        assertTrue(s.insertCards(0, d.getDevCards(1,0)));
        assertFalse(s.insertCards(1, d.getDevCards(1,0)));

    }

    @Test
    void checkUsage() throws IOException, GameFinishedException {
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
    void countTotalNumberDevCards() throws IOException, GameFinishedException {
        SlotDevCards s = new SlotDevCards();
        DevCardsDeck d = new DevCardsDeck();
        s.insertCards(0, d.getDevCards(2,0));
        s.insertCards(0, d.getDevCards(1,0));
        s.insertCards(1, d.getDevCards(2,1));

        assertEquals(3, s.countTotalNumberDevCards());

    }

    @Test
    void countVictoryPointsZero() throws IOException{
        SlotDevCards slotDevCards = new SlotDevCards();

        assertEquals(0, slotDevCards.countVictoryPoints());
    }

    @Test
    void countVictoryPointsOne() throws IOException, GameFinishedException {
        DevCardsDeck devCardsDeck = new DevCardsDeck();
        SlotDevCards slotDevCards = new SlotDevCards();
        slotDevCards.insertCards(0, devCardsDeck.getDevCards(2, 0));

        assertEquals(devCardsDeck.getDevCards(2, 0).getVictoryPoints(), slotDevCards.countVictoryPoints());
    }

    @Test
    void countVictoryPointsTree() throws IOException, GameFinishedException {
        DevCardsDeck devCardsDeck = new DevCardsDeck();
        SlotDevCards slotDevCards = new SlotDevCards();
        slotDevCards.insertCards(0, devCardsDeck.getDevCards(2,0));
        slotDevCards.insertCards(1, devCardsDeck.getDevCards(2,1));
        slotDevCards.insertCards(2, devCardsDeck.getDevCards(2,2));

        int numvic = devCardsDeck.getDevCards(2,0).getVictoryPoints() + devCardsDeck.getDevCards(2,1).getVictoryPoints() + devCardsDeck.getDevCards(2,2).getVictoryPoints();
        assertEquals(numvic, slotDevCards.countVictoryPoints());
    }

    @Test
    void countVictoryPointsMany() throws IOException, GameFinishedException {
        DevCardsDeck devCardsDeck = new DevCardsDeck();
        SlotDevCards slotDevCards = new SlotDevCards();
        slotDevCards.insertCards(0, devCardsDeck.getDevCards(2,0));
        slotDevCards.insertCards(1, devCardsDeck.getDevCards(2,1));
        slotDevCards.insertCards(2, devCardsDeck.getDevCards(2,2));
        slotDevCards.insertCards(0, devCardsDeck.getDevCards(1,0));
        slotDevCards.insertCards(1, devCardsDeck.getDevCards(1,1));
        slotDevCards.insertCards(2, devCardsDeck.getDevCards(1,2));

        int numvic = devCardsDeck.getDevCards(2,0).getVictoryPoints() + devCardsDeck.getDevCards(2,1).getVictoryPoints() + devCardsDeck.getDevCards(2,2).getVictoryPoints() + devCardsDeck.getDevCards(1,0).getVictoryPoints() + devCardsDeck.getDevCards(1,1).getVictoryPoints() + devCardsDeck.getDevCards(1,2).getVictoryPoints();
        assertEquals(numvic, slotDevCards.countVictoryPoints());
    }
}