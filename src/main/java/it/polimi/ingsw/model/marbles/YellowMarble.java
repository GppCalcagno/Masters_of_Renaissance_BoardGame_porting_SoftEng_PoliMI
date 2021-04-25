package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Coins;

public class YellowMarble extends Marbles {
    /**
     * This method converts a yellow marble into a coin
     * @param p reference to the player's Warehouse depots
     */
    @Override
    public boolean addtoWarehouse(Player p, int i) {
        return p.getWarehouse().checkInsertion(i, new Coins());
    }
}
