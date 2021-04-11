package it.polimi.ingsw.marbles;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Resources;
import it.polimi.ingsw.producible.Shields;
import it.polimi.ingsw.producible.Stones;

public class GreyMarble extends Marbles {
    /**
     * This method converts a grey marble into a stone
     * @param p reference to the player's Warehouse depots
     */
    @Override
    public void addtoWarehouse(Player p, int i) {
        p.getWarehouse().checkInsertion(i,new Stones());
    }
}
