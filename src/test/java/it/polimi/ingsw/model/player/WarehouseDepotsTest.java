package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.model.exceptions.OverflowQuantityExcepions;
import it.polimi.ingsw.model.producible.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseDepotsTest {
    Resources coin = new Coins();
    Resources shield = new Shields();
    Resources stone = new Stones();
    Resources servant = new Servants();

    //-------------------------------------checkInsertiontest------------------------------------
    @Test
    void Limit() {
        WarehouseDepots w = new WarehouseDepots();

        assertTrue(w.checkInsertion(0,coin));
        assertFalse(w.checkInsertion(0,coin));
        assertFalse(w.checkInsertion(0,coin));
        assertFalse(w.checkInsertion(0,coin));

        assertTrue(w.checkInsertion(1,shield));
        assertTrue(w.checkInsertion(1,shield));
        assertFalse(w.checkInsertion(1,shield));
        assertFalse(w.checkInsertion(1,shield));
        assertFalse(w.checkInsertion(1,shield));

        assertTrue(w.checkInsertion(2,stone));
        assertTrue(w.checkInsertion(2,stone));
        assertTrue(w.checkInsertion(2,stone));
        assertFalse(w.checkInsertion(2,stone));
        assertFalse(w.checkInsertion(2,stone));
    }

    @Test
    void RowTypeControl() {
        WarehouseDepots w = new WarehouseDepots();

        assertTrue(w.checkInsertion(1,shield));
        assertFalse(w.checkInsertion(1,stone));
        assertFalse(w.checkInsertion(1,coin));
        assertTrue(w.checkInsertion(1,shield));
    }

    @Test
    void AlreadyThere(){
        WarehouseDepots w = new WarehouseDepots();

        assertTrue(w.checkInsertion(0,coin));

        assertFalse(w.checkInsertion(1,coin));
        assertFalse(w.checkInsertion(2,coin));


    }
    //insertCoin non viene testata perchè è un metodo chimato solo in condizioni "protette"

    //-------------------------------------checkExchange------------------------------------

    @Test
    void Simply() {
        WarehouseDepots w = new WarehouseDepots();

        w.checkInsertion(1,shield);
        w.checkInsertion(0,coin);

        assertTrue(w.checkExchange(0,1));

        assertEquals(w.getWarehouse()[0][0],shield);
        assertEquals(w.getWarehouse()[1][0],coin);
    }

    @Test
    void TooMuchResources() {
        WarehouseDepots w = new WarehouseDepots();


        w.checkInsertion(0,coin);
        w.checkInsertion(1,stone);
        w.checkInsertion(1,stone);


        assertFalse(w.checkExchange(0,1));

        assertEquals(w.getWarehouse()[0][0],coin);
        assertEquals(w.getWarehouse()[1][0],stone);
        assertEquals(w.getWarehouse()[1][1],stone);
}

    @Test
    void bigger(){
        WarehouseDepots w = new WarehouseDepots();

        w.checkInsertion(1,stone);
        w.checkInsertion(1,stone);

        w.checkInsertion(2,coin);
        w.checkInsertion(2,coin);

        assertTrue(w.checkExchange(2,1));

        assertEquals(w.getWarehouse()[2][0],stone);
        assertEquals(w.getWarehouse()[2][1],stone);
        assertEquals(w.getWarehouse()[1][0],coin);
        assertEquals(w.getWarehouse()[1][1],coin);

    }

    //-------------------------------------delete------------------------------------

    @Test
    void simplydelete() {
        WarehouseDepots w = new WarehouseDepots();

        w.checkInsertion(1,stone);
        w.checkInsertion(1,stone);

        assertEquals(w.getWarehouse()[1][0],stone);
        assertEquals(w.getWarehouse()[1][1],stone);

        assertTrue(w.delete(stone));

        assertEquals(w.getWarehouse()[1][0],stone);
        assertNull(w.getWarehouse()[1][1]);
    }

    @Test
    void Noelement(){
        WarehouseDepots w = new WarehouseDepots();
        assertFalse(w.delete(stone));
    }

    //-------------------------------------getNumResources------------------------------------

    @Test
    void SimplyCount(){
        WarehouseDepots w = new WarehouseDepots();

        assertTrue(w.checkInsertion(0,new Coins()));

        assertEquals(1,w.getNumResources(new Coins()));
    }

    @Test
    void NullCheckCount(){
        WarehouseDepots w = new WarehouseDepots();

        assertEquals(0,w.getNumResources(new Coins()));
        assertEquals(0,w.getNumResources(new Shields()));
        assertEquals(0,w.getNumResources(new Servants()));
        assertEquals(0,w.getNumResources(new Stones()));
    }

    void GenericClassTestCount(){
        WarehouseDepots w = new WarehouseDepots();

        Resources coin = new Coins();
        Resources shield = new Shields();
        Resources stone = new Stones();
        Resources servant = new Servants();

        w.checkInsertion(2,coin);
        w.checkInsertion(2,coin);
        w.checkInsertion(2,coin);

        w.checkInsertion(1,shield);

        assertEquals(3,w.getNumResources(new Coins()));
        assertEquals(1,w.getNumResources(new Shields()));
    }


    @Test
    void CountwithDelete(){
        WarehouseDepots w = new WarehouseDepots();

        Resources coin = new Coins();
        Resources shield = new Shields();
        Resources stone = new Stones();
        Resources servant = new Servants();

        w.checkInsertion(2,coin);
        w.checkInsertion(2,coin);
        w.checkInsertion(2,coin);

        w.checkInsertion(1,shield);
        assertTrue(w.delete(coin));

        assertEquals(2,w.getNumResources(new Coins()));
        assertEquals(1,w.getNumResources(new Shields()));
    }

    @Test
    void ExtraChestCount() throws NegativeQuantityExceptions, OverflowQuantityExcepions {
        WarehouseDepots w = new WarehouseDepots();
        w.addleaderCardEffect(new Coins());
        w.checkInsertion(2,new Coins());

        w.getLeaderCardEffect().get(0).updateResources(1);
        assertEquals(2,w.getNumResources(new Coins()));
    }

    @Test
    void ExtraChestCount2() throws NegativeQuantityExceptions, OverflowQuantityExcepions {
        WarehouseDepots w = new WarehouseDepots();
        w.addleaderCardEffect(new Coins());
        w.addleaderCardEffect(new Stones());
        w.checkInsertion(2,new Coins());

        w.getLeaderCardEffect().get(0).updateResources(1);
        w.getLeaderCardEffect().get(1).updateResources(1);
        assertEquals(2,w.getNumResources(new Coins()));
        assertEquals(1,w.getNumResources(new Stones()));
    }
}