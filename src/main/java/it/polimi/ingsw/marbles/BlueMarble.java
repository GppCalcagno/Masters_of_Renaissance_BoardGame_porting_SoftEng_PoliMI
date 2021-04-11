package it.polimi.ingsw.marbles;

import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.producible.Resources;
import it.polimi.ingsw.producible.Shields;

public class BlueMarble extends Marbles {
    /**
     * This method converts a blue marble into a shield
     * @param p reference to the player's Warehouse depots
     */
    @Override
    public void addtoWarehouse(Player p, int i) {
        p.getWarehouse().checkInsertion(i, new Shields());
    }
}
