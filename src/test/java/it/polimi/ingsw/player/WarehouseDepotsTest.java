package it.polimi.ingsw.player;

import  static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.stub.secondStubResources;
import org.junit.jupiter.api.Test;

import stub.stubResources;


class WarehouseDepotsTest {

    @Test
    void getWarehouseNum() {
    }

    @Test
    void checkInsertion() throws CantDoIt {
        WarehouseDepots w = new WarehouseDepots();
        stubResources s = new stubResources(5);
        stubResources s1 = new stubResources(6);

        w.checkInsertion(1, s);
        w.checkInsertion(1, s);
        w.checkInsertion(1, s);

        w.checkInsertion(2,s1);
        w.checkInsertion(2,s1);


        assertEquals(w.getWarehouseNum(1), 2);
        assertEquals(w.getWarehouseNum(2), 2);
    }

    @Test
    void insertResourcesWithRightInserction(){
        WarehouseDepots w = new WarehouseDepots();
        stubResources s = new stubResources(5);

        w.insertResources(2, s);
        w.insertResources(2, s);
        w.insertResources(2, s);
        //ne metto solo tre e non di più perchè al massimo la riga 2 può contenere 3 elementi. e questo viene fattto dal controllo di checkInsertion()
        //w.insertResources(2, s);
        //w.insertResources(2, s);
        assertEquals(w.getWarehouseNum(2), 3);
    }

    @Test
    void checkExchange() throws CantDoIt {
        WarehouseDepots w = new WarehouseDepots();
        stubResources s = new stubResources(5);
        secondStubResources s1 = new secondStubResources();

        w.checkInsertion(1, s);

        w.checkInsertion(2, s1);
        w.checkInsertion(2, s1);

        w.checkExchange(1,2);

        assertEquals(2, w.getWarehouseNum(1));

    }

    @Test
    void exchange() {
    }

    @Test
    void delete() throws CantDoIt {
        WarehouseDepots w = new WarehouseDepots();
        stubResources s = new stubResources(5);

        w.checkInsertion(1, s);
        w.checkInsertion(1, s);
        w.checkInsertion(1, s);
        w.checkInsertion(1, s);

        //assertEquals(2,w.getWarehouseNum(1));

        w.delete(s);

        assertEquals(1,w.getWarehouseNum(1));
    }

   /* @Test
    void addLeaderCardEffect() throws CantDoIt{
        WarehouseDepots w = new WarehouseDepots();
        stubResources s = new stubResources(5);

        w.addLeaderCardEffect(s);

        w.show();
    }*/
}