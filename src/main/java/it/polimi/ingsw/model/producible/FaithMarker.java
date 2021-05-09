package it.polimi.ingsw.model.producible;

import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.player.Player;

public class FaithMarker implements Producible {
    /**
     * this method activates the effect of the resource: increases the player's faithMarker
     * @param p is the player who uses the Producible
     */
    public void effect(Player p) throws ActiveVaticanReportException {
        p.increasefaithMarker();
    }

    /**
     * this method is used to  get the key of the maps
     * @return the key used in game's map
     */
    public String toString() {
        return "FaithMarker";
    }
}
