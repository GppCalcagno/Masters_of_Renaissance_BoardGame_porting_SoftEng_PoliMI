package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.model.producible.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrongboxTest {
    Resources coins= new Coins();
    Resources servant= new Servants();
    Resources shield= new Shields();
    Resources stone= new Stones();

    @Test
    public  void initialization(){
        Strongbox strongTest= new Strongbox();
        assertEquals(strongTest.getNumResources(coins),0);
        assertEquals(strongTest.getNumResources(servant),0);
        assertEquals(strongTest.getNumResources(shield),0);
        assertEquals(strongTest.getNumResources(stone),0);
    }

    @Test
    public void simplyadd() throws NegativeQuantityExceptions {
        Strongbox strongTest= new Strongbox();

        strongTest.updateResources(coins, 5);
        assertEquals(strongTest.getNumResources(coins),5);

        strongTest.updateResources(coins, 3);

        assertEquals(strongTest.getNumResources(coins),8);
        assertEquals(strongTest.getNumResources(servant),0);
        assertEquals(strongTest.getNumResources(shield),0);
        assertEquals(strongTest.getNumResources(stone),0);

    }

    @Test
    public void simplyremove() throws NegativeQuantityExceptions {
        Strongbox strongTest= new Strongbox();

        strongTest.updateResources(coins, 5);
        assertEquals(strongTest.getNumResources(coins),5);

        strongTest.updateResources(coins, -3);

        assertEquals(strongTest.getNumResources(coins),2);
        assertEquals(strongTest.getNumResources(servant),0);
        assertEquals(strongTest.getNumResources(shield),0);
        assertEquals(strongTest.getNumResources(stone),0);

    }

    @Test
    public void testException(){
        Strongbox strongTest= new Strongbox();

        try {
            strongTest.updateResources(coins, -3);
        } catch (NegativeQuantityExceptions negativeQuantityExceptions) {
            assertTrue(true);
        }
    }


}