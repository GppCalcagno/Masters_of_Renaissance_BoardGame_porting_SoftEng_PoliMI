package it.polimi.ingsw.model.card.leadereffect;

import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.model.exceptions.OverflowQuantityExcepions;
import it.polimi.ingsw.model.producible.Coins;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExtraChestTest {

    @Test
    public void Simpleadd () throws OverflowQuantityExcepions, NegativeQuantityExceptions {
        ExtraChest test= new ExtraChest(new Coins());
        test.updateResources(1);
        assertEquals(1,test.getnum());
    }

    @Test
    public void OverflowQuantityExcepionsTest() throws NegativeQuantityExceptions {
        ExtraChest test= new ExtraChest(new Coins());
        try {
            test.updateResources(3);
        } catch (OverflowQuantityExcepions overflowQuantityExcepions) {
            assertTrue(true);
        }
    }

     @Test
    public void NegativeQuantityExceptionsTest() throws OverflowQuantityExcepions {
         ExtraChest test= new ExtraChest(new Coins());
             try {
                 test.updateResources(-2);
             } catch (NegativeQuantityExceptions negativeQuantityExceptions) {
                 assertTrue(true);
             }


     }

}