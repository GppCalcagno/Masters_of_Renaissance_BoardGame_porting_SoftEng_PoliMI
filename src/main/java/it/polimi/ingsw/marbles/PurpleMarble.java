package it.polimi.ingsw.marbles;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Resources;
import it.polimi.ingsw.producible.Servants;

public class PurpleMarble extends Marbles {
    /**
     * This method converts a purple marble into a servant
     * @param p reference to the player's Warehouse depots
     */
    @Override
    public void addtoWarehouse(Player p, int i) {
        p.getWarehouse().checkInsertion(i, new Servants());
    }
}
