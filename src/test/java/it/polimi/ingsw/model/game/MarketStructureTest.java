package it.polimi.ingsw.model.game;

import it.polimi.ingsw.model.marbles.BlueMarble;
import it.polimi.ingsw.model.marbles.Marbles;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarketStructureTest {

    @Test
    void initializeMarketStructure() throws IOException {
        MarketStructure marketStructure = new MarketStructure();

        assertNotEquals(null, marketStructure.getMarketTray()[0][0]);

        List<Marbles> marblesList = new ArrayList<>();
        for(int i = 0; i < 3; i++){
            for (int j = 0; j < 4; j++){
                marblesList.add(marketStructure.getMarketTray()[i][j]);
            }
        }
        assertEquals(12, marblesList.size());
    }

    @Test
    void extractMarblesColumn() throws IOException {
        MarketStructure marketTest = new MarketStructure();
        assertTrue(marketTest.extractMarbles('c', 2));

        assertFalse(marketTest.getBuffer().isEmpty());
    }

    @Test
    void extractMarblesRow() throws IOException {
        MarketStructure marketTest = new MarketStructure();
        assertTrue(marketTest.extractMarbles('r', 2));

        assertFalse(marketTest.getBuffer().isEmpty());
    }

    @Test
    void extractMarblesRowError() throws IOException {
        MarketStructure marketTest = new MarketStructure();
        try{
            assertFalse(marketTest.extractMarbles('r', 3));
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException){}
    }

    @Test
    void insertMarble() throws IOException {
        MarketStructure marketTest = new MarketStructure();
        Marbles temp = marketTest.getRemainingMarble();

        marketTest.insertMarble('r', 2);

        assertEquals(temp, marketTest.getMarketTray()[2][3]);
        assertNotEquals(temp, marketTest.getRemainingMarble());
    }

    @Test
    void discardMarbles() throws IOException {
        Marbles blue = new BlueMarble();
        MarketStructure marketTest = new MarketStructure();

        marketTest.getBuffer().add(blue);
        assertFalse(marketTest.getBuffer().isEmpty());

        marketTest.discardMarbles(blue);
        assertTrue(marketTest.getBuffer().isEmpty());
    }

    @Test
    void emptyBuffer() throws IOException {
        MarketStructure marketTest = new MarketStructure();

        marketTest.getBuffer().add(new BlueMarble());
        assertFalse(marketTest.getBuffer().isEmpty());

        marketTest.emptyBuffer();
        assertTrue(marketTest.getBuffer().isEmpty());
    }
}