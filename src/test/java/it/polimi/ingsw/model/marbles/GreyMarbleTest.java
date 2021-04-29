package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.model.exceptions.OverflowQuantityExcepions;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Coins;
import it.polimi.ingsw.model.producible.Shields;
import it.polimi.ingsw.model.producible.Stones;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GreyMarbleTest {

    @Test
    void addtoWarehouseTrue() throws ActiveVaticanReportException {
        Player player = new Player("Luca");
        Marbles marbles = new GreyMarble();

        assertEquals(0, player.getWarehouse().getNumResources(new Stones()));
        assertTrue(marbles.addtoWarehouse(player, 0));
        assertEquals(1, player.getWarehouse().getNumResources(new Stones()));
    }

    @Test
    void addtoWarehouseFalse() throws ActiveVaticanReportException {
        Player player = new Player("Luca");
        Marbles marbles = new GreyMarble();

        assertTrue(player.getWarehouse().checkInsertion(0, new Stones()));
        assertFalse(marbles.addtoWarehouse(player, 1));
    }

    @Test
    void addToExtraChestRight() throws NegativeQuantityExceptions, OverflowQuantityExcepions {
        Player player = new Player("Luca");
        Marbles marbles = new GreyMarble();
        player.getWarehouse().addleaderCardEffect(new Stones());

        assertTrue(marbles.addToExtraChest(player));
    }

    @Test
    void addToExtraChestEmpty() throws NegativeQuantityExceptions, OverflowQuantityExcepions {
        Player player = new Player("Luca");
        Marbles marbles = new GreyMarble();

        assertFalse(marbles.addToExtraChest(player));
    }

    @Test
    void addToExtraChestWrongResources() throws NegativeQuantityExceptions, OverflowQuantityExcepions {
        Player player = new Player("Luca");
        Marbles marbles = new GreyMarble();
        player.getWarehouse().addleaderCardEffect(new Coins());

        assertFalse(marbles.addToExtraChest(player));
    }

    @Test
    void addToExtraChestWrongOver() throws NegativeQuantityExceptions, OverflowQuantityExcepions {
        Player player = new Player("Luca");
        Marbles marbles = new GreyMarble();
        player.getWarehouse().addleaderCardEffect(new Stones());
        player.getWarehouse().getLeaderCardEffect().get(0).updateResources(2);

        assertFalse(marbles.addToExtraChest(player));
    }

    @Test
    void addToExtraChestFull() throws NegativeQuantityExceptions, OverflowQuantityExcepions {
        Player player = new Player("Luca");
        Marbles marbles = new GreyMarble();
        player.getWarehouse().addleaderCardEffect(new Stones());
        player.getWarehouse().getLeaderCardEffect().get(0).updateResources(2);
        player.getWarehouse().addleaderCardEffect(new Coins());

        assertFalse(marbles.addToExtraChest(player));
    }
}