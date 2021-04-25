package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.producible.Shields;

public class BlueMarble extends Marbles {
    /**
     * This method converts a blue marble into a shield
     * @param p reference to the player's Warehouse depots
     */
    @Override
    public boolean addtoWarehouse(Player p, int i) {
        return p.getWarehouse().checkInsertion(i, new Shields());
    }
}
