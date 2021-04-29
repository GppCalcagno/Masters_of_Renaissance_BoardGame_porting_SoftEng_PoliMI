package it.polimi.ingsw.model.marbles;


import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.model.exceptions.OverflowQuantityExcepions;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Coins;
import it.polimi.ingsw.model.producible.Servants;
import it.polimi.ingsw.model.producible.Shields;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlueMarbleTest {

    @Test
    void addtoWarehouseTrue() throws ActiveVaticanReportException {
        Player player = new Player("Luca");
        Marbles marbles = new BlueMarble();

        assertEquals(0, player.getWarehouse().getNumResources(new Shields()));
        assertTrue(marbles.addtoWarehouse(player, 0));
        assertEquals(1, player.getWarehouse().getNumResources(new Shields()));
    }

    @Test
    void addtoWarehouseFalse() throws ActiveVaticanReportException {
        Player player = new Player("Luca");
        Marbles marbles = new BlueMarble();

        assertTrue(player.getWarehouse().checkInsertion(0, new Shields()));
        assertFalse(marbles.addtoWarehouse(player, 1));

    }

    @Test
    void addToExtraChestRight() throws NegativeQuantityExceptions, OverflowQuantityExcepions {
        Player player = new Player("Luca");
        Marbles marbles = new BlueMarble();
        player.getWarehouse().addleaderCardEffect(new Shields());

        assertTrue(marbles.addToExtraChest(player));
    }

    @Test
    void addToExtraChestEmpty() throws NegativeQuantityExceptions, OverflowQuantityExcepions {
        Player player = new Player("Luca");
        Marbles marbles = new BlueMarble();

        assertFalse(marbles.addToExtraChest(player));
    }

    @Test
    void addToExtraChestWrongResources() throws NegativeQuantityExceptions, OverflowQuantityExcepions {
        Player player = new Player("Luca");
        Marbles marbles = new BlueMarble();
        player.getWarehouse().addleaderCardEffect(new Coins());

        assertFalse(marbles.addToExtraChest(player));
    }

    @Test
    void addToExtraChestWrongOver() throws NegativeQuantityExceptions, OverflowQuantityExcepions {
        Player player = new Player("Luca");
        Marbles marbles = new BlueMarble();
        player.getWarehouse().addleaderCardEffect(new Shields());
        player.getWarehouse().getLeaderCardEffect().get(0).updateResources(2);

        assertFalse(marbles.addToExtraChest(player));
    }

    @Test
    void addToExtraChestFull() throws NegativeQuantityExceptions, OverflowQuantityExcepions {
        Player player = new Player("Luca");
        Marbles marbles = new BlueMarble();
        player.getWarehouse().addleaderCardEffect(new Shields());
        player.getWarehouse().getLeaderCardEffect().get(0).updateResources(2);
        player.getWarehouse().addleaderCardEffect(new Coins());

        assertFalse(marbles.addToExtraChest(player));
    }
}