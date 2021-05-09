package it.polimi.ingsw.model.producible;

import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;

public interface Producible {
    /**
     * this method activates the effect of the Producible
     * @param p is the player who uses the Producible
     */
    public void effect(Player p) throws ActiveVaticanReportException;

    /**
     * this method is used to  get the key of the maps
     * @return the key used in game's map
     */
    public String toString();
}

