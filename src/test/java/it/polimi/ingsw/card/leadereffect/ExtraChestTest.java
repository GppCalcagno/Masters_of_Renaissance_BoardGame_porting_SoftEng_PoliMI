package it.polimi.ingsw.card.leadereffect;

import it.polimi.ingsw.exceptions.OverflowQuantityExcepions;
import it.polimi.ingsw.producible.Coins;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtraChestTest {

    @Test
    public void Simpleadd () throws OverflowQuantityExcepions {
        ExtraChest test= new ExtraChest(new Coins());
        test.updateResources(1);
        assertEquals(1,test.getnum());
    }

    @Test
    public void OverflowQuantityExcepionsTest(){
        ExtraChest test= new ExtraChest(new Coins());
        try {
            test.updateResources(3);
        } catch (OverflowQuantityExcepions overflowQuantityExcepions) {
            assertTrue(true);
        }
    }

}