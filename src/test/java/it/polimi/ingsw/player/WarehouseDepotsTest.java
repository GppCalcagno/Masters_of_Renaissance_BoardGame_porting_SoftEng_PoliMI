package it.polimi.ingsw.player;

import it.polimi.ingsw.card.leadereffect.ExtraChest;
import it.polimi.ingsw.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.exceptions.OverflowQuantityExcepions;
import it.polimi.ingsw.producible.*;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseDepotsTest {

    @Test
    void getWarehouseNum() {
    }

    @Test
    void checkInsertion() {
        WarehouseDepots w = new WarehouseDepots();
        Resources r1 = new Coins();
        Resources r2 = new Shields();
        Resources r3 = new Stones();
        Resources r4 = new Servants();

        assertTrue(w.checkInsertion(0,r1));
        assertFalse(w.checkInsertion(0,r1));
        assertTrue(w.checkInsertion(1,r2));
        assertTrue(w.checkInsertion(1,r2));
        assertFalse(w.checkInsertion(1,r3));
        assertFalse(w.checkInsertion(2,r1));

        assertTrue(w.checkInsertion(2,r4));
        assertTrue(w.checkInsertion(2,r4));
        assertTrue(w.checkInsertion(2,r4));
    }

    @Test
    void insertResources() {
        WarehouseDepots w = new WarehouseDepots();
        Resources r1 = new Coins();
        Resources r2 = new Shields();
        Resources r3 = new Stones();
        Resources r4 = new Servants();

        assertTrue(w.insertResources(0, r1));
        assertFalse(w.insertResources(0, r1));
        assertTrue(w.insertResources(1, r2));
        assertTrue(w.insertResources(1, r2));
        assertFalse(w.insertResources(1, r2));
        assertFalse(w.insertResources(1, r2));


    }

    @Test
    void checkExchange() {
        WarehouseDepots w = new WarehouseDepots();
        Resources r1 = new Coins();
        Resources r2 = new Shields();
        Resources r3 = new Stones();
        Resources r4 = new Servants();

        w.checkInsertion(0,r1);
        w.checkInsertion(1,r2);

        assertTrue(w.checkExchange(0,1));

        w.checkInsertion(2,r3);
        w.checkInsertion(2,r3);
        w.checkInsertion(2,r3);
        assertFalse(w.checkExchange(2,0));
    }

    @Test
    void exchange() {
        WarehouseDepots w = new WarehouseDepots();
        Resources r1 = new Coins();
        Resources r2 = new Shields();
        Resources r3 = new Stones();
        Resources r4 = new Servants();

        w.checkInsertion(0,r1);
        w.checkInsertion(1,r2);

        w.exchange(0,1);

        assertEquals(w.getWarehouse()[0][0],r2);
    }

    @Test
    void delete() {
        WarehouseDepots w = new WarehouseDepots();
        Resources r1 = new Coins();
        Resources r2 = new Shields();
        Resources r3 = new Stones();
        Resources r4 = new Servants();

        w.checkInsertion(2,r1);
        w.checkInsertion(2,r1);
        w.checkInsertion(2,r1);

        assertTrue(w.delete(r1));

       assertEquals(2, w.getNumResources(r1));
    }

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