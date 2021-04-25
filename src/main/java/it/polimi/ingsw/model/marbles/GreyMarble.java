package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Stones;

public class GreyMarble extends Marbles {
    /**
     * This method converts a grey marble into a stone
     * @param p reference to the player's Warehouse depots
     */
    @Override
    public boolean addtoWarehouse(Player p, int i) {
        return p.getWarehouse().checkInsertion(i,new Stones());
    }
}
