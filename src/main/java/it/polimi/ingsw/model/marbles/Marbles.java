package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.player.Player;

public abstract class Marbles {
    /**
     * This is the constructor method
     */
    public Marbles(){}

    /**
     * This method converts the caught Marbles into resources and adds them to Warehouse depot
     * @param p reference to the player's Warehouse depots
     */
    public abstract boolean addtoWarehouse (Player p, int i) throws ActiveVaticanReportException;
}
