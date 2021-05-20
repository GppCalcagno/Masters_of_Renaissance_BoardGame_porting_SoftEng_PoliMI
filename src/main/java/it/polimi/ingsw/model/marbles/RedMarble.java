package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.model.exceptions.OverflowQuantityExcepions;
import it.polimi.ingsw.model.player.Player;

public class RedMarble extends Marbles {
    /**
     * This method converts a red marble into a faith point
     * @param p reference to the player's Warehouse depots
     */
    @Override
    public boolean addtoWarehouse(Player p, int i) throws ActiveVaticanReportException {
        if (p.getFaithMarker() == 24)
            return false;
        else {
            p.increasefaithMarker();
            return true;
        }
    }

    /**
     * This method hasn't to be invoked from the Red Marble
     * @param p reference to the player's Extra Chest
     * @return always false
     */
    @Override
    public boolean addToExtraChest(Player p) {
        return false;
    }

    @Override
    public String toString() {
        return "FaithMarker";
    }
}
