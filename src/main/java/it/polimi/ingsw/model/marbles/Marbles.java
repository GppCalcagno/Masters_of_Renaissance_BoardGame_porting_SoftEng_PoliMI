package it.polimi.ingsw.model.marbles;

import it.polimi.ingsw.model.exceptions.ActiveVaticanReportException;
import it.polimi.ingsw.model.exceptions.NegativeQuantityExceptions;
import it.polimi.ingsw.model.exceptions.OverflowQuantityExcepions;
import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;

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

    /**
     * This method adds the converted Resource from the caught Marble to the Extra Chest, if the relative Leader Card is activated
     * @param p reference to the player's Extra Chest
     * @return true if the add is successful
     */
    public abstract boolean addToExtraChest (Player p);


    public String toString(){
        return "abstract";
    }
}
