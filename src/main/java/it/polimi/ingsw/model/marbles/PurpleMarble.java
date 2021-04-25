package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Servants;

public class PurpleMarble extends Marbles {
    /**
     * This method converts a purple marble into a servant
     * @param p reference to the player's Warehouse depots
     */
    @Override
    public boolean addtoWarehouse(Player p, int i) {
        return p.getWarehouse().checkInsertion(i, new Servants());
    }
}
