package it.polimi.ingsw.game;

import it.polimi.ingsw.StubGiovanni.MarketStructureStub;
import it.polimi.ingsw.marbles.BlueMarble;
import it.polimi.ingsw.marbles.Marbles;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MarketStructureTest {

    @Test
    void extractMarblesColumn() {
        MarketStructure marketTest = new MarketStructureStub(new BlueMarble());
        marketTest.extractMarbles('c', 2);

        assertFalse(marketTest.getBuffer().isEmpty());

        System.out.println("Il buffer è lungo: " + marketTest.getBuffer().size() + " = sizex");
    }

    @Test
    void extractMarblesRow() {
        MarketStructure marketTest = new MarketStructureStub(new BlueMarble());
        marketTest.extractMarbles('r', 2);

        assertFalse(marketTest.getBuffer().isEmpty());

        System.out.println("Il buffer è lungo: " + marketTest.getBuffer().size() + " = sizey");
    }

    @Test
    void insertMarble() {
        Marbles blue = new BlueMarble();
        MarketStructure marketTest = new MarketStructureStub(blue);

        assertEquals(blue, marketTest.getRemainingMarble());

        marketTest.insertMarble('r', 2);

        assertEquals(blue, marketTest.getMarketTray()[2][3]);
        assertNotEquals(blue, marketTest.getRemainingMarble());
    }

    @Test
    void discardMarbles() {
        Marbles blue = new BlueMarble();
        MarketStructure marketTest = new MarketStructureStub(blue);

        marketTest.getBuffer().add(blue);
        assertFalse(marketTest.getBuffer().isEmpty());


        marketTest.discardMarbles(blue);
        assertTrue(marketTest.getBuffer().isEmpty());
    }

    @Test
    void emptyBuffer() {
        Marbles blue = new BlueMarble();
        MarketStructure marketTest = new MarketStructureStub(blue);

        marketTest.emptyBuffer();
        assertTrue(marketTest.getBuffer().isEmpty());
    }
}